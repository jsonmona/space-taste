package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class MatzipBasicInfoDTO {
    private String name;
    private String bcode;
    private String baseAddress;
    private String detailAddress;
    private List<String> hashtags;
    private String photoBase64;
}
