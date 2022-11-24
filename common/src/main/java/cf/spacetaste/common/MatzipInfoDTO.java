package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class MatzipInfoDTO {
    private int matzipId;
    private String name;
    private String baseAddress;
    private String detailAddress;
    private List<String> hashtags;
    private String photoUrl;
}
