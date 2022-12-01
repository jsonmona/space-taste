package cf.spacetaste.app.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StarGroup {

    private float scoreTaste;
    private float scorePrice;
    private float scoreKindness;
    private float scoreClean;

    public float average() {
        return (scoreTaste + scorePrice + scoreKindness + scoreClean) / 4;
    }
}
