package cf.spacetaste.app.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class MatzipReview {
    private final int reviewId;
    private final int matzipId;
    private final int userId;
    private final String username;
    private final int scoreTaste;
    private final int scorePrice;
    private final int scoreKindness;
    private final int scoreClean;
    private final String detail;
    private final String nickname;
    private final String profileUrl;
}
