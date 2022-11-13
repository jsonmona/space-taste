package cf.spacetaste.app.data;

import android.net.Uri;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MatzipCreateRequest {
    private String name;
    private String bcode;
    private String baseAddress;
    private String detailAddress;
    private ArrayList<String> hashtags;
    private Uri photo;

    public void validate() {
        if (name == null || bcode == null || baseAddress == null || detailAddress == null)
            throw new NullPointerException();

        if (hashtags == null)
            hashtags = new ArrayList<>();
    }
}
