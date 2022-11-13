package cf.spacetaste.common;

import lombok.Data;
import lombok.NonNull;

@Data
public class AuthRequestDTO {
    @NonNull
    private String kakaoAccessToken;
}
