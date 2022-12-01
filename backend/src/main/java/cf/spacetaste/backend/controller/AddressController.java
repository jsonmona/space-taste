package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.mapper.AddressMapper;
import cf.spacetaste.common.AddressInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AddressController {

    private final AddressMapper addressMapper;

    @GetMapping("/address/arealist")
    public List<AddressInfoDTO> listServiceArea() {
        return addressMapper.listServiceArea()
                .stream()
                .map((x) -> new AddressInfoDTO(
                        x.getAddressCode(),
                        x.getSiDo(),
                        x.getSiGunGu(),
                        x.getEupMyeonDong(),
                        x.getDongRi()
                ))
                .toList();
    }
}
