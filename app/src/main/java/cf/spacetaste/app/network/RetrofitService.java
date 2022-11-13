package cf.spacetaste.app.network;

import cf.spacetaste.common.AuthRequestDTO;
import cf.spacetaste.common.AuthResponseDTO;
import cf.spacetaste.common.MatzipBasicInfoDTO;
import cf.spacetaste.common.MatzipInfoDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("matzip")
    Call<MatzipInfoDTO> createMatzip(@Body MatzipBasicInfoDTO info);

    @GET("/user/auth")
    Call<AuthResponseDTO> checkUserAuth(@Body AuthRequestDTO info);
}