package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    public GroupService groupService;

    @GetMapping("")
    public String showGroupsList(Model model) {
        List<Group> groupList = groupService.listAllGroups();
        model.addAttribute("groupsList", groupList);
        return "group/groups-list";
    }

    // обработчик на получение формы для добавления студента
    @GetMapping("/new")
    public String showNewGroupForm(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("group", new Group());
        return "group/group-form";
    }

    // обработчик для сохранения данных о пользователе
    @PostMapping("/save")
    public String saveNewGroup(@ModelAttribute("group") Group group, RedirectAttributes ra) {
        // 1. сохраняем группу в БД
        Group saved = groupService.saveGroup(group);
        // 2. добавить сообщение о том, что группа сохранена
        ra.addFlashAttribute("message",
                "Group " + saved.getGroupName() + " saved successfully");
        // 3. выполнить перенаправление
        return "redirect:/groups";
    }

    @GetMapping("/update/{id}")
    public String showUpdateStudentForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("group", groupService.findById(id));
        return "group/group-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra) {
        groupService.deleteGroupById(id);
        ra.addFlashAttribute("message", "Group deleted");
        return "redirect:/groups";
    }
}
