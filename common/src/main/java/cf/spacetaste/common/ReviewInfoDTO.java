package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ReviewInfoDTO {
    private int reviewId;
    private int matzipId;
    private int userId;
    private int scoreTaste;
    private int scorePrice;
    private int scoreKindness;
    private int scoreClean;
    private String detail;
    private String nickname;
    private String profileUrl;
}
