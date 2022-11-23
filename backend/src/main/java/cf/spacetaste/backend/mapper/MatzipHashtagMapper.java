package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.HashtagModel;
import cf.spacetaste.backend.model.MatzipModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatzipHashtagMapper {

    @Insert("INSERT INTO matzip_hashtag (matzip_id, hashtag_id) VALUES (#{matzip.matzipId}, #{hashtag.hashtagId)")
    int link(MatzipModel matzip, HashtagModel hashtag);

    @Delete("DELETE FROM matzip_hashtag WHERE matzip_id=#{matzip.matzipId} AND hashtag_id=#{hashtag.hashtagId}")
    int unlink(MatzipModel matzip, HashtagModel hashtag);

    @Select("SELECT b.content FROM matzip_hashtag AS a NATURAL JOIN hashtag AS b WHERE a.matzip_id=1")
    List<String> listAsString(MatzipModel matzip);
}
