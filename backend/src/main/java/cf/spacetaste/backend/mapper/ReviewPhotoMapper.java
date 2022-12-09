package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.PhotoModel;
import cf.spacetaste.backend.model.ReviewModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewPhotoMapper {

    @Select("SELECT b.photo_id AS photo_id FROM review AS a INNER JOIN review_photo AS b ON a.review_id = b.review_id WHERE a.matzip_id=#{matzipId} LIMIT 10")
    List<PhotoModel> listPhotoWithMatzip(int matzipId);

    @Insert("INSERT INTO review_photo (review_id, photo_id) VALUES (#{review.reviewId}, #{photo.photoId})")
    int attachPhoto(ReviewModel review, PhotoModel photo);
}
