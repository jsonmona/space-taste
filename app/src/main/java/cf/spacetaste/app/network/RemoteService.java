package cf.spacetaste.app.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import cf.spacetaste.app.data.AuthResponse;
import cf.spacetaste.app.data.MatzipCreateRequest;
import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.data.StarGroup;
import cf.spacetaste.common.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 오직 AppState 에서 사용하기 위해 만들어진 클래스입니다.
 * 직접 사용하지 마세요.
 *
 * @see cf.spacetaste.app.AppState
 */
public class RemoteService {
    private static final String TAG = "RemoteService";
    private static final String SERVER_URL = "https://api.space-taste.cf";

    private Context context;
    private Looper mainLooper;
    private Handler handler;
    private ExecutorService es;
    private Retrofit retrofit;
    private RetrofitService service;
    private String token;

    public RemoteService(Context context) {
        this.context = context;
        this.mainLooper = context.getMainLooper();
        this.handler = new Handler(mainLooper);
        this.es = Executors.newCachedThreadPool();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        this.service = retrofit.create(RetrofitService.class);
        this.token = null;
    }

    private void runOnUiThread(Runnable r) {
        if (Thread.currentThread() == mainLooper.getThread()) {
            r.run();
        } else {
            handler.post(r);
        }
    }

    private String auth() {
        return "Bearer " + token;
    }

    public boolean isLoggedIn() {
        return token != null;
    }

    public void logout() {
        token = null;
    }

    public void checkUserAuth(String kakaoAccessToken, AsyncResultPromise<AuthResponse> cb) {
        service.checkUserAuth(kakaoAccessToken).enqueue(new Callback<AuthResponseDTO>() {
            @Override
            public void onResponse(Call<AuthResponseDTO> call, Response<AuthResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    token = response.body().getToken();
                    runOnUiThread(() -> cb.onResult(true, new AuthResponse(response.body().isNewUser())));
                } else {
                    Log.e(TAG, "failed with status=" + response.code());
                    runOnUiThread(() -> cb.onResult(false, null));
                }
            }

            @Override
            public void onFailure(Call<AuthResponseDTO> call, Throwable t) {
                t.printStackTrace();
                runOnUiThread(() -> cb.onResult(false, null));
            }
        });
    }

    public void createMatzip(MatzipCreateRequest req, AsyncResultPromise<MatzipInfo> cb) {
        if (!isLoggedIn()) {
            Log.e(TAG, "createMatzip: not logged in");
            cb.onResult(false, null);
            return;
        }

        es.submit(() -> {
            try {
                String photoData = null;
                if (req.getPhoto() != null) {
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    Base64OutputStream encoder = new Base64OutputStream(bout, Base64.NO_PADDING | Base64.NO_WRAP | Base64.URL_SAFE);
                    byte[] buf = new byte[4096];
                    try (InputStream in = context.getContentResolver().openInputStream(req.getPhoto())) {
                        while (true) {
                            int len = in.read(buf);
                            if (len <= 0)
                                break;
                            encoder.write(buf, 0, len);
                        }
                        encoder.flush();
                        photoData = bout.toString("US-ASCII");
                    }
                }

                MatzipBasicInfoDTO info = new MatzipBasicInfoDTO(
                        req.getName(),
                        req.getBcode(),
                        req.getBaseAddress(),
                        req.getDetailAddress(),
                        req.getHashtags(),
                        photoData
                );
                service.createMatzip(auth(), info).enqueue(new Callback<MatzipInfoDTO>() {
                    @Override
                    public void onResponse(Call<MatzipInfoDTO> call, Response<MatzipInfoDTO> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            MatzipInfo info = new MatzipInfo(response.body());
                            runOnUiThread(() -> cb.onResult(true, info));
                        } else {
                            System.err.println("failed with status=" + response.code());
                            runOnUiThread(() -> cb.onResult(false, null));
                        }
                    }

                    @Override
                    public void onFailure(Call<MatzipInfoDTO> call, Throwable t) {
                        t.printStackTrace();
                        runOnUiThread(() -> cb.onResult(false, null));
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void listMatzipPhotos(MatzipInfo info, AsyncResultPromise<List<String>> cb) {
        service.listMatzipPhotos(token, info.getMatzipId())
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            runOnUiThread(() -> cb.onResult(true, response.body()));
                        } else {
                            Log.e(TAG, "Failed to list matzip photos with code=" + response.code());
                            runOnUiThread(() -> cb.onResult(false, null));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Log.e(TAG, "Failed to list matzip photos", t);
                        runOnUiThread(() -> cb.onResult(false, null));
                    }
                });
    }

    public void searchMatzip(List<String> tags, String term, AsyncResultPromise<List<MatzipInfo>> cb) {
        SearchRequestDTO req = new SearchRequestDTO(tags, term);
        service.searchMatzip(token, req).enqueue(new Callback<List<MatzipInfoDTO>>() {
            @Override
            public void onResponse(Call<List<MatzipInfoDTO>> call, Response<List<MatzipInfoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<MatzipInfo> res = new ArrayList<>(response.body().size());
                    for (MatzipInfoDTO info : response.body()) {
                        res.add(new MatzipInfo(info));
                    }
                    runOnUiThread(() -> cb.onResult(true, res));
                } else {
                    Log.e(TAG, "Failed to list matzip photos with code=" + response.code());
                    runOnUiThread(() -> cb.onResult(false, null));
                }
            }

            @Override
            public void onFailure(Call<List<MatzipInfoDTO>> call, Throwable t) {
                Log.e(TAG, "Failed to search", t);
                runOnUiThread(() -> cb.onResult(false, null));
            }
        });
    }

    public void listServiceArea(AsyncResultPromise<List<AddressInfoDTO>> cb) {
        service.listServiceArea().enqueue(new Callback<List<AddressInfoDTO>>() {
            @Override
            public void onResponse(Call<List<AddressInfoDTO>> call, Response<List<AddressInfoDTO>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e(TAG, "Failed to list service area with code="+response.code());
                    runOnUiThread(() -> cb.onResult(false, null));
                } else {
                    runOnUiThread(() -> cb.onResult(true, response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<AddressInfoDTO>> call, Throwable t) {
                Log.e(TAG, "Failed to list service area", t);
                cb.onResult(false, null);
            }
        });
    }

    public void postReview(MatzipInfo matzip, ReviewInfoDTO review, AsyncNotifyPromise cb) {
        review.setReviewId(0);
        review.setMatzipId(matzip.getMatzipId());
        review.setUserId(0);

        service.postReview(token, review).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Failed to post review with code="+response.code());
                    runOnUiThread(() -> cb.onResult(false));
                } else {
                    runOnUiThread(() -> cb.onResult(true));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Failed to pose review", t);
                runOnUiThread(() -> cb.onResult(false));
            }
        });
    }

    public void getUserInfo(AsyncResultPromise<UserInfoDTO> cb) {
        service.getUserInfo(token).enqueue(new Callback<UserInfoDTO>() {
            @Override
            public void onResponse(Call<UserInfoDTO> call, Response<UserInfoDTO> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e(TAG, "Failed to get user info with code="+response.code());
                    runOnUiThread(() -> cb.onResult(false, null));
                } else {
                    runOnUiThread(() -> cb.onResult(true, response.body()));
                }
            }

            @Override
            public void onFailure(Call<UserInfoDTO> call, Throwable t) {
                Log.e(TAG, "Failed to get user info", t);
                runOnUiThread(() -> cb.onResult(false, null));
            }
        });
    }
}
