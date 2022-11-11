package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.model.UserModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MatzipMapper {

    @Select("SELECT * FROM matzip WHERE matzip_id = #{id} AND deleted_by IS NULL")
    MatzipModel getFromId(int id);

    int create(MatzipModel matzip, @Param("user") UserModel initiator);

    int update(MatzipModel matzip, @Param("user") UserModel initiator);

    @Update("UPDATE SET deleted_at=NOW(), deleted_by=#{user.id} WHERE matzip_id=#{matzip.id}")
    int delete(MatzipModel matzip, @Param("user") UserModel initiator);
}
