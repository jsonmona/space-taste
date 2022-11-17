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
import cf.spacetaste.common.AuthResponseDTO;
import cf.spacetaste.common.MatzipBasicInfoDTO;
import cf.spacetaste.common.MatzipInfoDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        return token == null;
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
                    System.err.println("failed with status="+response.code());
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
                    Base64OutputStream encoder = new Base64OutputStream(bout, Base64.NO_PADDING | Base64.NO_WRAP);
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
                            MatzipInfoDTO body = response.body();
                            MatzipInfo info = new MatzipInfo(
                                    body.getMatzipId(),
                                    body.getName(),
                                    body.getAddress(),
                                    "",
                                    body.getHashtags(),
                                    body.getPhotoUrl()
                            );
                            runOnUiThread(() -> cb.onResult(true, info));
                        } else {
                            System.err.println("failed with status="+response.code());
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
}
