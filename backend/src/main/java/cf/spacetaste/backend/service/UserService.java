package cf.spacetaste.backend.service;

import cf.spacetaste.backend.external.KakaoApi;
import cf.spacetaste.backend.mapper.UserMapper;
import cf.spacetaste.backend.model.PhotoModel;
import cf.spacetaste.backend.model.UserModel;
import cf.spacetaste.common.AuthResponseDTO;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.IOException;

@Service
public class UserService {
    
    private UserMapper userMapper;
    private PhotoService photoService;
    private Retrofit retrofit;
    private KakaoApi kakaoApi;
    
    public UserService(UserMapper userMapper, PhotoService photoService) {
        this.userMapper = userMapper;
        this.photoService = photoService;

        retrofit =  new Retrofit.Builder()
                .baseUrl("https://kapi.kakao.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        kakaoApi = retrofit.create(KakaoApi.class);
    }
    
    public AuthResponseDTO auth(String kakaoAccessToken) {
        try {
            var tokenInfo = kakaoApi.tokenInfo("Bearer " + kakaoAccessToken).execute();

            if (!tokenInfo.isSuccessful() || tokenInfo.body() == null)
                return null;

            long kakaoId = tokenInfo.body().getId();

            boolean isNew = userMapper.getFromKakaoId(kakaoId) == null;
            if (isNew) {
                var profileInfo = kakaoApi.profileInfo("Bearer "+kakaoAccessToken).execute();

                String nickname = null;
                String profileImage = null;

                if (profileInfo.isSuccessful() && profileInfo.body() != null) {
                    var body = profileInfo.body();
                    if (body.getProperties() != null) {
                        nickname = body.getProperties().getNickname();
                        profileImage = body.getProperties().getThumbnail_image();
                    }
                }

                if (nickname == null)
                    nickname = "익명";

                UserModel user = new UserModel(0, kakaoId, nickname, null, null, null);
                userMapper.create(user);

                if (profileImage != null) {
                    PhotoModel photo = photoService.createPhotoWithURL(profileImage);
                    if (photo != null) {
                        userMapper.attachProfilePhoto(user, photo);
                    }
                }
            }

            return new AuthResponseDTO(isNew);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
