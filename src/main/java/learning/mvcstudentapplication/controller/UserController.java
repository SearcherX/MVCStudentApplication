package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.db.entity.security.Role;
import learning.mvcstudentapplication.db.entity.security.User;
import learning.mvcstudentapplication.service.security.RoleService;
import learning.mvcstudentapplication.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public String showUsersList(Model model) {
        model.addAttribute("usersList", userService.listAll());
        return "user/users-list";
    }

    // обработчик для сохранения данных о юзере
    @PostMapping("/save")
    public String saveUser(User user, RedirectAttributes ra,
                              @RequestParam String action) {
        // 1. сохраняем юзера в БД
        User saved = userService.saveUser(user);
        // 2. добавить сообщение о том, что студент сохранен
        ra.addFlashAttribute("message",
                "User " + saved.getUsername() + " " + action + "d successfully");
        // 3. выполнить перенаправление
        return "redirect:/users/list";
    }

    //обработчик на получение формы для обновления юзера
    @GetMapping("/update/{id}")
    public String showUpdateUserForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("user", userService.findById(id));
        List<Role> roles = roleService.listAll();  // список всех ролей
        model.addAttribute("rolesList", roles);
        return "user/user-form";
    }

    // обработчик для удаления юзера
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra) {
        userService.deleteUserById(id);
        ra.addFlashAttribute("message", "User deleted");
        return "redirect:/users/list";
    }
}
