package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.db.entity.Subject;
import learning.mvcstudentapplication.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    public SubjectService subjectService;

    @GetMapping("")
    public String showGroupsList(Model model) {
        List<Subject> subjects = subjectService.listAll();
        model.addAttribute("subjectsList", subjects);
        return "subject/subjects-list";
    }
}