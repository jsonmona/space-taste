package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.PhotoModel;
import cf.spacetaste.backend.model.UserModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE user_id=#{id}")
    UserModel getFromId(int id);

    @Select("SELECT * FROM user WHERE kakao_id=#{kakaoId}")
    UserModel getFromKakaoId(long kakaoId);

    @Insert("INSERT INTO user(kakao_id, nickname) VALUES (#{kakaoId}, #{nickname})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int create(UserModel user);

    @Update("UPDATE user SET profile_photo=#{photo.photoId} WHERE user_id=#{user.userId}")
    int attachProfilePhoto(UserModel user, PhotoModel photo);
}
