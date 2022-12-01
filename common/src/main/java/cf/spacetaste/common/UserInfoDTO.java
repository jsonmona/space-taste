package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class UserInfoDTO {
    @NonNull
    private String username;

    /** 지정되지 않았으면 null */
    private String profilePhotoUrl;

    /** 지정되지 않았으면 null */
    private AddressInfoDTO activeArea;

    /** 지정되지 않았으면 null */
    private AddressInfoDTO livingArea;
}
