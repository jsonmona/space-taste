package cf.spacetaste.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class UserModel {
    private int userId;
    private Long kakaoId;
    private String nickname;
    private Integer profilePhoto;

    /** 활동지역 */
    private String addressCode1;

    /** 생활지역 */
    private String addressCode2;
}
