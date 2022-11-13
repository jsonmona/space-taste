package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.service.MatzipService;
import cf.spacetaste.backend.service.UserService;
import cf.spacetaste.common.AuthRequestDTO;
import cf.spacetaste.common.AuthResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/auth")
    public AuthResponseDTO auth(@RequestBody AuthRequestDTO req) {
        System.out.println("Mocking API: "+req);
        AuthResponseDTO res = new AuthResponseDTO();
        res.setNewUser(true);
        return res;
    }
}
