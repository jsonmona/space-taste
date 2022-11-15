package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE user_id=#{id} AND deleted_by IS NULL")
    UserModel getFromId(int id);

    int create(UserModel user);

    @Update("UPDATE user SET deleted_at=NOW(), deleted_by=#{user.id} WHERE user_id=#{user.id}")
    int delete(UserModel user);
}
