package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.service.MatzipService;
import cf.spacetaste.backend.service.PhotoService;
import cf.spacetaste.backend.service.TokenService;
import cf.spacetaste.common.MatzipBasicInfoDTO;
import cf.spacetaste.common.MatzipInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
public class MatzipController {

    private final MatzipService matzipService;
    private final PhotoService photoService;
    private final TokenService tokenService;

    private MatzipInfoDTO queryInfo(MatzipModel matzip) {
        var hashtags = matzipService.getHashtags(matzip);
        String photoUrl = null;
        if (matzip.getMainPhoto() != null)
            photoUrl = photoService.makeUrl(photoService.getFromId(matzip.getMainPhoto()));

        return new MatzipInfoDTO(
                matzip.getMatzipId(),
                matzip.getName(),
                matzip.getAddressBase(),
                matzip.getAddressDetail(),
                hashtags,
                photoUrl
        );
    }

    @PostMapping("/matzip")
    public MatzipInfoDTO createMatzip(@RequestHeader("authorization") String auth, @RequestBody MatzipBasicInfoDTO info) {
        int userId = tokenService.verifyToken(auth);
        var matzip = matzipService.createMatzip(info);
        if (matzip == null || matzip.getMatzipId() <= 0)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        return queryInfo(matzip);
    }
}
