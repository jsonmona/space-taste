package cf.spacetaste.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AverageStarModel {

    private float taste;
    private float price;
    private float kindness;
    private float clean;
}
