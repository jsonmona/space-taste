package cf.spacetaste.backend.external;

import lombok.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface KakaoApi {
    @GET("/v1/user/access_token_info")
    Call<TokenInfo> tokenInfo(@Header("Authorization") String auth);

    @POST("/v2/user/me")
    Call<ProfileInfo> profileInfo(@Header("Authorization") String auth);

    @Data
    class TokenInfo {
        private long id;
        private int expires_in;
        private int app_id;
    }

    @Data
    class ProfileInfo {
        @Data
        public static class Inner {
            String nickname;
            String thumbnail_image;
        }

        Inner properties;
    }
}
