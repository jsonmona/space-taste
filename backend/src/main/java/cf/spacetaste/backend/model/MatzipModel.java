package cf.spacetaste.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class MatzipModel {
    private int matzipId;
    private String name;
    private String addressCode;
    private String addressBase;
    private String addressDetail;
    private Integer mainPhoto;
    private Float averageScoreTaste;
    private Float averageScorePrice;
    private Float averageScoreGentle;
    private Float averageScoreClean;
}
