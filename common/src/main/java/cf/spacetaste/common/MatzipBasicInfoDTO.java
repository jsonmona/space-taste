package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class MatzipBasicInfoDTO {
    private String name;
    private String address;
    private List<String> hashtags;
    private String photoBase64;
}
