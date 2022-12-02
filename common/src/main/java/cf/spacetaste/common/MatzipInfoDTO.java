package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class MatzipInfoDTO {
    private int matzipId;
    private String name;
    private String baseAddress;
    private String detailAddress;
    private List<String> hashtags;
    private String photoUrl;
    private float scoreTaste;
    private float scorePrice;
    private float scoreKindness;
    private float scoreClean;

    /// true -> 별점이 있음, false -> 별점이 없음 (리뷰가 하나도 없음)
    private boolean hasScore;
}
