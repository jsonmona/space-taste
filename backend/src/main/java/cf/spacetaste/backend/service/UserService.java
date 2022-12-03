package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.AddressMapper;
import cf.spacetaste.backend.mapper.UserMapper;
import cf.spacetaste.backend.model.PhotoModel;
import cf.spacetaste.backend.model.UserModel;
import cf.spacetaste.common.AddressInfoDTO;
import cf.spacetaste.common.AuthResponseDTO;
import cf.spacetaste.common.UserInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final String kakaoUrl = "https://kapi.kakao.com";
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final PhotoService photoService;
    private final TokenService tokenService;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @AllArgsConstructor
    @Getter
    static class TokenInfo {
        private long id;
        private int expiresIn;
        private int appId;
    }

    @AllArgsConstructor
    @Getter
    static class ProfileInfo {
        private String nickname;
        private String thumbnailImage;
    }

    private TokenInfo getTokenInfo(String kakaoAccessToken) throws IOException {
        var req = new Request.Builder()
                .get()
                .url(kakaoUrl + "/v1/user/access_token_info")
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .build();

        try (var res = okHttpClient.newCall(req).execute()) {
            if (!res.isSuccessful() || res.body() == null)
                return null;

            var obj = objectMapper.readTree(res.body().byteStream());

            try {
                return new TokenInfo(obj.get("id").asLong(), obj.get("expires_in").asInt(), obj.get("app_id").asInt());
            } catch(NullPointerException e) {
                return null;
            }
        }
    }

    private ProfileInfo getProfileInfo(String kakaoAccessToken) throws IOException {
        var req = new Request.Builder()
                .get()
                .url(kakaoUrl + "/v2/user/me")
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .build();

        try (var res = okHttpClient.newCall(req).execute()) {
            if (!res.isSuccessful() || res.body() == null)
                return null;

            var obj = objectMapper.readTree(res.body().byteStream());
            var properties = obj.get("properties");

            String nickname = null;
            String thumbnailImage = null;

            if (properties != null) {
                if (properties.hasNonNull("nickname"))
                    nickname = properties.get("nickname").asText();
                if (properties.hasNonNull("thumbnail_image"))
                    thumbnailImage = properties.get("thumbnail_image").asText();
            }

            return new ProfileInfo(nickname, thumbnailImage);
        }
    }
    
    public AuthResponseDTO auth(String kakaoAccessToken) {
        try {
            TokenInfo tokenInfo = getTokenInfo(kakaoAccessToken);

            if (tokenInfo == null)
                return null;

            long kakaoId = tokenInfo.getId();

            UserModel user = userMapper.getFromKakaoId(kakaoId);
            boolean isNew = user == null || user.getUserId() == 0;

            if (isNew) {
                ProfileInfo profileInfo = getProfileInfo(kakaoAccessToken);

                String nickname = null;
                String profileImage = null;

                if (profileInfo != null) {
                    nickname = profileInfo.getNickname();
                    profileImage = profileInfo.getThumbnailImage();
                }

                if (nickname == null)
                    nickname = "익명";

                user = new UserModel(0, kakaoId, nickname, null, null, null);
                userMapper.create(user);

                if (profileImage != null) {
                    PhotoModel photo = photoService.createPhotoWithURL(profileImage);
                    if (photo != null) {
                        userMapper.attachProfilePhoto(user, photo);
                    }
                }
            }

            return new AuthResponseDTO(isNew, tokenService.createToken(user.getUserId()));
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserInfoDTO getInfo(int userId) {
        UserModel user = userMapper.getFromId(userId);

        if (user == null)
            return null;

        String photoUrl;
        if (user.getProfilePhoto() != null)
            photoUrl = photoService.makeUrl(photoService.getFromId(user.getProfilePhoto()));
        else
            photoUrl = null;

        return new UserInfoDTO(
                user.getNickname(),
                photoUrl,
                addressService.getInfo(user.getAddressCode1()),
                addressService.getInfo(user.getAddressCode2())
        );
    }

    public boolean changeArea(int userId, String activeArea, String livingArea) {
        if (activeArea != null && addressMapper.checkExistsCode(activeArea) == 0)
            return false;

        if (livingArea != null && addressMapper.checkExistsCode(livingArea) == 0)
            return false;

        return userMapper.changeArea(userId, activeArea, livingArea) != 0;
    }
}
