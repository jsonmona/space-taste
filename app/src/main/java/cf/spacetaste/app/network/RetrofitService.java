package cf.spacetaste.app.network;

import cf.spacetaste.common.MatzipBasicInfoDTO;
import cf.spacetaste.common.MatzipInfoDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("matzip")
    Call<MatzipInfoDTO> createMatzip(@Body MatzipBasicInfoDTO info);
}
