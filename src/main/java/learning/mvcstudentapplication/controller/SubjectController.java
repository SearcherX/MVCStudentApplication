package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.db.entity.Subject;
import learning.mvcstudentapplication.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    public SubjectService subjectService;

    //обработчик на получение списка предметов, учитывая фильтр
    @GetMapping("")
    public String showGroupsList(@RequestParam(required = false, defaultValue = "")
                                     String containsFilter, Model model) {
        List<Subject> subjects;

        if (containsFilter != null && !containsFilter.isEmpty())
            subjects = subjectService.findByContains(containsFilter);
        else
            subjects = subjectService.listAll();

        model.addAttribute("subjectsList", subjects);
        model.addAttribute("containsFilter", containsFilter);
        return "subject/subjects-list";
    }
}