package cf.spacetaste.app.network;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Base64OutputStream;
import cf.spacetaste.app.data.MatzipInfo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteService {
    private static final String SERVER_URL = "https://api.space-taste.cf";

    private Context context;
    private ExecutorService es;
    private Retrofit retrofit;
    private RetrofitService service;

    public RemoteService(Context context) {
        this.context = context;
        this.es = Executors.newCachedThreadPool();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        this.service = retrofit.create(RetrofitService.class);
    }
    public void createMatzip(String name, String baseAddress, String detailAddress, List<String> hashtags, Uri photo, AsyncResultPromise<MatzipInfo> cb) {
        es.submit(() -> {
            try {
                String photoData = null;
                if (photo != null) {
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    Base64OutputStream encoder = new Base64OutputStream(bout, Base64.NO_PADDING | Base64.NO_WRAP);
                    byte[] buf = new byte[4096];
                    try (InputStream in = context.getContentResolver().openInputStream(photo)) {
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

                MatzipBasicInfoDTO info = new MatzipBasicInfoDTO(name, baseAddress + " " + detailAddress, hashtags, photoData);
                service.createMatzip(info).enqueue(new Callback<MatzipInfoDTO>() {
                    @Override
                    public void onResponse(Call<MatzipInfoDTO> call, Response<MatzipInfoDTO> response) {
                        if (response.isSuccessful()) {
                            MatzipInfoDTO body = response.body();
                            MatzipInfo info = new MatzipInfo();
                            info.setMatzipId(body.getMatzipId());
                            info.setName(body.getName());
                            info.setAddress(body.getAddress());
                            info.setHashtags(body.getHashtags());
                            info.setPhotoUrl(body.getPhotoUrl());
                            cb.onResult(true, info);
                        }
                    }

                    @Override
                    public void onFailure(Call<MatzipInfoDTO> call, Throwable t) {
                        t.printStackTrace();
                        cb.onResult(false, null);
                    }
                });
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
    }
}
