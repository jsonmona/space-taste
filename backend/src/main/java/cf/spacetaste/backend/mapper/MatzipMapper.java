package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.model.UserModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MatzipMapper {

    @Select("SELECT * FROM matzip WHERE matzip_id = #{id} LIMIT 1")
    List<MatzipModel> getFromId(int id);

    @Insert("INSERT INTO matzip (name, address_code, address_base, address_detail, main_photo) VALUES (#{name}, #{addressCode}, #{addressBase}, #{addressDetail}, #{mainPhoto})")
    @Options(useGeneratedKeys = true, keyProperty = "matzipId")
    int create(MatzipModel matzip);

    //int update(MatzipModel matzip);

    @Delete("DELETE FROM matzip WHERE matzip_id=#{matzipId}")
    int delete(MatzipModel matzip);
}
