package cf.spacetaste.app.data;

import android.net.Uri;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewCreationInfo {
    private int scoreTaste;
    private int scorePrice;
    private int scoreKindness;
    private int scoreClean;
    private String detail;
    private List<Uri> photos;
}
