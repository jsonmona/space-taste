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

    @GET("/matzip/{matzipId}/photo")
    Call<List<String>> listMatzipPhotos(@Header("Authorization") String token, @Path("matzipId") int matzipId);

    @POST("/search/v1")
    Call<List<MatzipInfoDTO>> searchMatzip(@Header("Authorization") String token, @Body SearchRequestDTO info);

    @GET("/address/arealist")
    Call<List<AddressInfoDTO>> listServiceArea();

    @POST("/matzip/{matzipId}/review")
    Call<Void> postReview(@Header("Authorization") String token, @Path("matzipId") int matzipId, @Body CreateReviewRequestDTO info);

    @GET("/user")
    Call<UserInfoDTO> getUserInfo(@Header("Authorization") String token);

    @GET("/search/reviewuser")
    Call<List<MatzipInfoDTO>> searchByReviewedUser(@Header("Authorization") String token);

    @PUT("/user/area")
    Call<Void> changeUserArea(@Header("Authorization") String token, @Body ChangeAreaRequestDTO info);

    @GET("/matzip/{matzipId}/review")
    Call<List<ReviewInfoDTO>> listReviewOfMatzip(@Header("Authorization") String token, @Path("matzipId") int matzipId);

    @GET("/search/randomtags")
    Call<List<String>> getRandomTags(@Header("Authorization") String token);

    @GET("/hashtag/photo")
    Call<List<String>> getMainPhotoOfTag(@Header("Authorization") String token, @Query("tag") String tag);
}
