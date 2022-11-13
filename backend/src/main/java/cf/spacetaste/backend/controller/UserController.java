package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.service.UserService;
import cf.spacetaste.common.AuthResponseDTO;
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
    public AuthResponseDTO auth(@RequestParam String kakaoAccessToken) {
        System.out.println("Mocking API: "+ kakaoAccessToken);
        AuthResponseDTO res = new AuthResponseDTO();
        res.setNewUser(true);
        return res;
    }
}
