package cf.spacetaste.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
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
