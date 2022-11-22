package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.db.entity.Group;
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

    //обработчик на получение списка групп, учитывая фильтр
    @GetMapping("")
    public String showGroupsList(@RequestParam(required = false, defaultValue = "") String containsFilter,
                                 Model model) {
        List<Group> groupList;

        if (containsFilter != null && !containsFilter.isEmpty())
            groupList = groupService.findByContains(containsFilter);
        else
            groupList = groupService.listAllGroups();

        model.addAttribute("groupsList", groupList);
        model.addAttribute("containsFilter", containsFilter);
        return "group/groups-list";
    }

    // обработчик на получение формы для добавления группы
    @GetMapping("/new")
    public String showNewGroupForm(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("group", new Group());
        return "group/group-form";
    }

    // обработчик для сохранения данных о группе
    @PostMapping("/save")
    public String saveNewGroup(@ModelAttribute("group") Group group, RedirectAttributes ra,
                               @RequestParam String action) {
        // 1. сохраняем группу в БД
        Group saved = groupService.saveGroup(group);
        // 2. добавить сообщение о том, что группа сохранена
        ra.addFlashAttribute("message",
                "Group " + saved.getGroupName() + " " + action + "d successfully");
        // 3. выполнить перенаправление
        return "redirect:/groups";
    }

    //обработчик на получение формы для обновления группы
    @GetMapping("/update/{id}")
    public String showUpdateStudentForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("group", groupService.findById(id));
        return "group/group-form";
    }

    //обработчик для удаления группы
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra) {
        groupService.deleteGroupById(id);
        ra.addFlashAttribute("message", "Group deleted");
        return "redirect:/groups";
    }
}
