package cf.spacetaste.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ReviewWithUserInfoModel {
    private int reviewId;
    private int matzipId;
    private int userId;
    private byte scoreTaste;
    private byte scorePrice;
    private byte scoreKindness;
    private byte scoreClean;
    private String detail;
    private String nickname;
    private Integer profilePhoto;
}
