package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.mapper.ReviewPhotoMapper;
import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.model.PhotoModel;
import cf.spacetaste.backend.service.MatzipService;
import cf.spacetaste.backend.service.PhotoService;
import cf.spacetaste.backend.service.TokenService;
import cf.spacetaste.common.MatzipBasicInfoDTO;
import cf.spacetaste.common.MatzipInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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

        float scoreTaste;
        float scorePrice;
        float scoreKindness;
        float scoreClean;
        boolean hasScore;

        try {
            scoreTaste = matzip.getAverageScoreTaste();
            scorePrice = matzip.getAverageScorePrice();
            scoreKindness = matzip.getAverageScoreKindness();
            scoreClean = matzip.getAverageScoreClean();
            hasScore = true;
        } catch (NullPointerException e) {
            scoreTaste = 0;
            scorePrice = 0;
            scoreKindness = 0;
            scoreClean = 0;
            hasScore = false;
        }

        return new MatzipInfoDTO(
                matzip.getMatzipId(),
                matzip.getName(),
                matzip.getAddressBase(),
                matzip.getAddressDetail(),
                hashtags,
                photoUrl,
                scoreTaste,
                scorePrice,
                scoreKindness,
                scoreClean,
                hasScore
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

    @GetMapping("/matzip/{id}")
    public MatzipInfoDTO queryMatzip(@PathVariable int id) {
        var matzip = matzipService.queryFromId(id);
        if (matzip == null || matzip.getMatzipId() <= 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return queryInfo(matzip);
    }

    @GetMapping("/matzip/{id}/photo")
    public List<String> queryMatzipPhotos(@PathVariable int id) {
        var matzip = matzipService.queryFromId(id);
        if (matzip == null || matzip.getMatzipId() <= 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return matzipService.listPhotoUrl(matzip);
    }
}
