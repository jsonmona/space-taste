package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.*;
import cf.spacetaste.backend.model.HashtagModel;
import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.model.PhotoModel;
import cf.spacetaste.common.MatzipBasicInfoDTO;
import cf.spacetaste.common.MatzipInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatzipService {

    private final MatzipMapper matzipMapper;
    private final MatzipHashtagMapper matzipHashtagMapper;
    private final HashtagMapper hashtagMapper;
    private final PhotoService photoService;
    private final ReviewPhotoMapper reviewPhotoMapper;

    public boolean checkExists(int matzipId) {
        return matzipMapper.checkIdExists(matzipId) != 0;
    }

    public MatzipModel createMatzip(MatzipBasicInfoDTO info) {
        Integer photoId = null;
        String photoBase64 = info.getPhotoBase64();
        if (photoBase64 != null && photoBase64.length() != 0) {
            byte[] image = Base64.getUrlDecoder().decode(photoBase64);
            PhotoModel photo = photoService.createPhotoWithBytes(image);
            if (photo != null && photo.getPhotoId() > 0)
                photoId = photo.getPhotoId();
        }

        MatzipModel model = new MatzipModel(
                0,
                info.getName(),
                info.getBcode(),
                info.getBaseAddress(),
                info.getDetailAddress(),
                photoId,
                null, null, null, null
        );
        int affected = matzipMapper.create(model);
        if (affected <= 0 || model.getMatzipId() <= 0)
            return null;

        for (String hashtagContent: info.getHashtags()) {
            for (HashtagModel curr : hashtagMapper.getFromContent(hashtagContent)) {
                matzipHashtagMapper.link(model, curr);
            }
        }

        return model;
    }

    public MatzipModel queryFromId(int id) {
        return matzipMapper.getFromId(id);
    }

    public List<String> getHashtags(MatzipModel matzip) {
        return matzipHashtagMapper.listAsString(matzip);
    }

    public List<String> listPhotoUrl(MatzipModel matzip) {
        return reviewPhotoMapper.listPhotoWithMatzip(matzip.getMatzipId())
                .stream()
                .map(photoService::makeUrl)
                .toList();
    }

    public List<MatzipInfoDTO> listByReviewedUser(int userId) {
        return matzipMapper.listByReviewedUser(userId)
                .stream()
                .map(this::modelToInfo)
                .toList();
    }

    public MatzipInfoDTO modelToInfo(MatzipModel matzip) {
        var hashtags = getHashtags(matzip);
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
}
