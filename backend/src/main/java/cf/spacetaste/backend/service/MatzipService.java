package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.MatzipMapper;
import org.springframework.stereotype.Service;

@Service
public class MatzipService {

    private MatzipMapper matzipMapper;

    public MatzipService(MatzipMapper matzipMapper) {
        this.matzipMapper = matzipMapper;
    }
}
