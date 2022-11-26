package cf.spacetaste.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ReviewModel {
    private int reviewId;
    private int matzipId;
    private int userId;
    private byte scoreFlavor;
    private byte scoreTaste;
    private byte scoreKindness;
    private byte scoreClean;
    private String detail;
}
