package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class MatzipInfoDTO {
    private int matzipId;
    private String name;
    private String address;
    private ArrayList<String> hashtags;
    private String photoUrl;
}
