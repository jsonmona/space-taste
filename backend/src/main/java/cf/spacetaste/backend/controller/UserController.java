package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.service.UserService;
import cf.spacetaste.common.AuthResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
}
