package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class GroupController {
    @Autowired
    public GroupService groupService;

    @GetMapping("/groups")
    public String showGroupsList(Model model) {
        List<Group> groupList = groupService.listAllGroups();
        model.addAttribute("groupsList", groupList);
        return "group/groups-list";
    }

    // обработчик на получение формы для добавления студента
    @GetMapping("/groups/new")
    public String showNewGroupForm(Model model) {
        model.addAttribute("group", new Group());
        return "group/group-form";
    }

    // обработчик для сохранения данных о пользователе
    @PostMapping("/groups/new")
    public String saveNewStudent(Group group, RedirectAttributes ra) {
        // 1. сохраняем нового студента в БД
        Group saved = groupService.saveGroup(group);
        // 2. добавить сообщение о том, что студент добавлен
        ra.addFlashAttribute("message",
                "Group " + saved.getGroupName() + " saved successfully");
        // 3. выполнить перенаправление
        return "redirect:/groups";
    }

    @GetMapping("/groups/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra) {
        groupService.deleteGroupById(id);
        ra.addFlashAttribute("message", "Group deleted");
        return "redirect:/groups";
    }
}
