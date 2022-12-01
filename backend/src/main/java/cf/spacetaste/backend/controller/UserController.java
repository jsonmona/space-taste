package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.service.TokenService;
import cf.spacetaste.backend.service.UserService;
import cf.spacetaste.common.AuthResponseDTO;
import cf.spacetaste.common.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("/user/auth")
    public ResponseEntity<AuthResponseDTO> auth(@RequestParam String kakaoAccessToken) {
        AuthResponseDTO result = userService.auth(kakaoAccessToken);

        if (result != null) {
            return ResponseEntity.ok()
                    .header("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0")
                    .body(result);
        } else {
            return ResponseEntity.status(401)
                    .header("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0")
                    .build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoDTO> info(@RequestHeader("Authorization") String auth) {
        int userId = tokenService.verifyToken(auth);
        UserInfoDTO result = userService.getInfo(userId);

        if (result != null) {
            return ResponseEntity.ok()
                    .header("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0")
                    .body(result);
        } else {
            return ResponseEntity.internalServerError()
                    .header("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0")
                    .build();
        }
    }
}
