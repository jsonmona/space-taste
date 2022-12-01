package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ReviewInfoDTO {
    /** 생성을 위한 요청시에는 0으로 설정 */
    private int reviewId;

    private int matzipId;
    private int userId;
    private int scoreTaste;
    private int scorePrice;
    private int scoreKindness;
    private int scoreClean;
    private String detail;
}
