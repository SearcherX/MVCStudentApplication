package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.db.entity.security.User;
import learning.mvcstudentapplication.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "layout/registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDb = userService.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("message", "User " + user.getUsername() + " exists!");
            return "layout/registration";
        }

        userService.addUser(user);

        return "redirect:/login";
    }
}
