package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.AddressModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {

    @Select("SELECT * FROM address WHERE address_code LIKE '1129%' AND address_code != '1129000000' ORDER BY eup_myeon_dong ASC LIMIT 1000")
    List<AddressModel> listServiceArea();
}
