package cf.spacetaste.app.network;

import cf.spacetaste.common.AuthResponseDTO;
import cf.spacetaste.common.MatzipBasicInfoDTO;
import cf.spacetaste.common.MatzipInfoDTO;
import retrofit2.Call;
import retrofit2.http.*;

public interface RetrofitService {
    @POST("/matzip")
    Call<MatzipInfoDTO> createMatzip(@Header("Authorization") String token, @Body MatzipBasicInfoDTO info);

    @GET("/user/auth")
    Call<AuthResponseDTO> checkUserAuth(@Query("kakaoAccessToken") String kakaoAccessToken);
}
