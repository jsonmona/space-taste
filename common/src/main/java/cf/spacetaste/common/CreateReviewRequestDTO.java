package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CreateReviewRequestDTO {
    private int scoreTaste;
    private int scorePrice;
    private int scoreKindness;
    private int scoreClean;
    private String detail;
    private List<String> photoEncoded;
}
