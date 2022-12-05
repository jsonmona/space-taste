package cf.spacetaste.backend.mapper;


import cf.spacetaste.backend.model.PhotoModel;
import cf.spacetaste.backend.model.UserModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PhotoMapper {
    @Insert("INSERT INTO photo () VALUES ()")
    @Options(useGeneratedKeys = true, keyProperty = "photoId")
    int create(PhotoModel photo);

    @Delete("DELETE FROM photo WHERE photo_id=#{photoId}")
    int delete(PhotoModel photo);

    @Select("SELECT COUNT(*) FROM photo WHERE photo_id=#{photoId}")
    int checkExists(int photoId);
}
