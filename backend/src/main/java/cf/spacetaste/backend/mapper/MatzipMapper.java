package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.model.UserModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MatzipMapper {

    @Select("SELECT * FROM matzip WHERE matzip_id = #{id} AND deleted_by IS NULL")
    MatzipModel getFromId(int id);

    @Insert("INSERT INTO matzip (name, address_code, address_base, address_detail, main_photo) VALUES (#{name}, #{addressCode}, #{addressBase}, #{addressDetail}, #{mainPhoto}")
    @Options(useGeneratedKeys = true, keyProperty = "matzipId")
    int create(MatzipModel matzip);

    //int update(MatzipModel matzip);

    @Update("UPDATE SET deleted_at=NOW(), deleted_by=#{user.id} WHERE matzip_id=#{matzipId}")
    int delete(MatzipModel matzip);
}
