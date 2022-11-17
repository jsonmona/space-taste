package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class AuthResponseDTO {
    private boolean isNewUser;
}
