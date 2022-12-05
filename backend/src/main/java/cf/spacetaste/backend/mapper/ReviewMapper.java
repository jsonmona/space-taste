package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.ReviewModel;
import cf.spacetaste.backend.model.ReviewWithUserInfoModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapper {

    @Select("SELECT * FROM review WHERE review_id=#{reviewId} LIMIT 1")
    ReviewModel getFromId(int reviewId);

    @Select("SELECT a.*, b.nickname, b.profile_photo FROM review AS a NATURAL JOIN user AS b WHERE matzip_id=#{matzipId} ORDER BY a.review_id DESC LIMIT 50")
    List<ReviewWithUserInfoModel> listFromMatzipWithNickname(int matzipId);

    @Select("SELECT * FROM review WHERE user_id=#{userId} ORDER BY review_id DESC LIMIT 50")
    List<ReviewModel> listFromUser(int userId);

    @Insert("INSERT INTO review (matzip_id, user_id, score_taste, score_price, score_kindness, score_clean, detail) VALUES (#{matzipId}, #{userId}, #{scoreTaste}, #{scorePrice}, #{scoreKindness}, #{scoreClean}, #{detail})")
    @Options(useGeneratedKeys = true, keyProperty = "reviewId")
    int create(ReviewModel review);

}
