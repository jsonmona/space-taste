package cf.spacetaste.backend.model;

import lombok.Data;

@Data
public class MatzipModel {
    private int id;
    private String name;
    private String address;
    private float averageScoreTaste;
    private float averageScorePrice;
    private float averageScoreGentle;
    private float averageScoreClean;
}
