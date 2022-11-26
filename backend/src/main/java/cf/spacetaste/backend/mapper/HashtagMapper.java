package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.HashtagModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HashtagMapper {
    @Select("SELECT DISTINCT * FROM hashtag WHERE content=#{content} LIMIT 100")
    List<HashtagModel> getFromContent(String content);

    @Select("SELECT DISTINCT * FROM hashtag WHERE content LIKE CONCAT('%', #{content}, '%') LIMIT 100")
    List<HashtagModel> searchContent(String content);
}
