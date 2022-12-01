package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.ReviewModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapper {

    @Select("SELECT * FROM review WHERE review_id=#{reviewId} LIMIT 1")
    ReviewModel getFromId(int reviewId);

    @Select("SELECT * FROM review WHERE matzip_id=#{matzipId} LIMIT 1000")
    List<ReviewModel> listFromMatzip(int matzipId);

    @Select("SELECT * FROM review WHERE user_id=#{userId} LIMIT 1000")
    List<ReviewModel> listFromUser(int userId);

    @Insert("INSERT INTO review (matzip_id, user_id, score_taste, score_price, score_kindness, score_clean, detail) VALUES (#{matzipId}, #{userId}, #{scoreTaste}, #{scorePrice}, #{scoreKindness}, #{scoreClean}, #{detail})")
    @Options(useGeneratedKeys = true, keyProperty = "reviewId")
    int create(ReviewModel review);

}
