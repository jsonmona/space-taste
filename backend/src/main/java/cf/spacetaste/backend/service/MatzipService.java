package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.MatzipHashtagMapper;
import cf.spacetaste.backend.mapper.MatzipMapper;
import cf.spacetaste.backend.model.HashtagModel;
import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.model.PhotoModel;
import cf.spacetaste.common.MatzipBasicInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatzipService {

    private final MatzipMapper matzipMapper;
    private final MatzipHashtagMapper matzipHashtagMapper;
    private final PhotoService photoService;

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
        return model;
    }

    public List<String> getHashtags(MatzipModel matzip) {
        return matzipHashtagMapper.listAsString(matzip);
    }
}
