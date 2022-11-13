package cf.spacetaste.app.data;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MatzipInfo {
    private int matzipId;
    private String name;
    private String baseAddress;
    private String detailAddress;
    private ArrayList<String> hashtags;

    // maybe null
    private String photoUrl;
}
