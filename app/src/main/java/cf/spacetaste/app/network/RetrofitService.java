package cf.spacetaste.app.network;

import cf.spacetaste.common.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitService {

    @GET("/user/auth")
    Call<AuthResponseDTO> checkUserAuth(@Query("kakaoAccessToken") String kakaoAccessToken);

    @POST("/matzip")
    Call<MatzipInfoDTO> createMatzip(@Header("Authorization") String token, @Body MatzipBasicInfoDTO info);

    @GET("/matzip/{matzipId}/photos")
    Call<List<String>> listMatzipPhotos(@Header("Authorization") String token, @Path("matzipId") int matzipId);

    @POST("/search/v1")
    Call<List<MatzipInfoDTO>> searchMatzip(@Header("Authorization") String token, @Body SearchRequestDTO info);

    @GET("/address/arealist")
    Call<List<AddressInfoDTO>> listServiceArea();

    @POST("/matzip/{matzipId}/review")
    Call<Void> postReview(@Header("Authorization") String token, @Body ReviewInfoDTO info);
}
