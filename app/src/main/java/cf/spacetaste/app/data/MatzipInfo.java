package cf.spacetaste.app.data;

import java.io.Serializable;
import java.util.List;

import cf.spacetaste.common.MatzipInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class MatzipInfo implements Serializable {

    public MatzipInfo(MatzipInfoDTO dto) {
        matzipId = dto.getMatzipId();
        name = dto.getName();
        baseAddress = dto.getBaseAddress();
        detailAddress = dto.getDetailAddress();
        hashtags = dto.getHashtags();
        photoUrl = dto.getPhotoUrl();

        if (dto.isHasScore()) {
            star = new StarGroup(
                    dto.getScoreTaste(),
                    dto.getScorePrice(),
                    dto.getScoreKindness(),
                    dto.getScoreClean()
            );
        } else {
            star = null;
        }
    }

    private final int matzipId;

    @NonNull
    private final String name;

    @NonNull
    private final String baseAddress;

    @NonNull
    private final String detailAddress;

    @NonNull
    private final List<String> hashtags;

    /** 사진이 없으면 null */
    private final String photoUrl;

    /** (리뷰가 없는 등의 이유로) 별점이 없으면 null */
    private final StarGroup star;
}
