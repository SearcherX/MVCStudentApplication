package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // контроллер для тестирования пользователей
    @Autowired
    private UserService userService;
    @PostMapping("/users/new")
    public String addUser(@RequestParam String username, @RequestParam String password) {
        userService.addUser(username, password);
        return "user added";
    }
}
