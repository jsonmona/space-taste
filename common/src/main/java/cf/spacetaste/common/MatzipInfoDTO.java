package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class MatzipInfoDTO {
    private int matzipId;
    private String name;
    private String address;
    private ArrayList<String> hashtags;
    private String photoUrl;
}
