package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.AddressMapper;
import cf.spacetaste.backend.model.AddressModel;
import cf.spacetaste.common.AddressInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressMapper addressMapper;

    public AddressInfoDTO getInfo(String code) {
        if (code == null)
            return null;

        AddressModel model = addressMapper.getFromCode(code);

        if (model == null)
            return null;

        return new AddressInfoDTO(
                code,
                model.getSiDo(),
                model.getSiGunGu(),
                model.getEupMyeonDong(),
                model.getDongRi()
        );
    }
}
