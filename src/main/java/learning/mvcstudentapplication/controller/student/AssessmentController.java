package learning.mvcstudentapplication.controller.student;

import learning.mvcstudentapplication.db.entity.Assessment;
import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Subject;
import learning.mvcstudentapplication.service.AssessmentService;
import learning.mvcstudentapplication.service.GroupService;
import learning.mvcstudentapplication.service.StudentService;
import learning.mvcstudentapplication.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students/assessments/{id}")
public class AssessmentController {
    @Autowired
    public StudentService studentService;

    @Autowired
    public AssessmentService assessmentService;

    @Autowired
    public SubjectService subjectService;

    @GetMapping("")
    public String showAssessmentList(Model model, @PathVariable("id") Integer id) {
        System.out.println("test");
        List<Assessment> assessmentList = assessmentService.listByStudentId(id);
        model.addAttribute("assessmentList", assessmentList);
        model.addAttribute("student", studentService.findById(id));
        return "assessment/assessments-list";
    }

    @GetMapping("/new")
    public String showAssessmentForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("assessment", new Assessment());
        model.addAttribute("studentId", id);
        List<Subject> subjects = subjectService.listAll();  // список всех предметов
        model.addAttribute("subjectsList", subjects);
        return "assessment/assessment-form";
    }

    @PostMapping("/save")
    public String saveAssessment(@ModelAttribute("assessment") Assessment assessment, RedirectAttributes ra,
                                 @RequestParam String action) {
        // 1. сохраняем новую оценку в БД
        Assessment saved = assessmentService.save(assessment);
        // 2. добавить сообщение о том, что оценка добавлена
        ra.addFlashAttribute("message",
                "Assessment " + saved.getAssessmentValue() + " on subject " +
                        saved.getSubject() + " " + action + "d successfully");
        // 3. выполнить перенаправление
        return "redirect:/students/assessments/" + assessment.getStudent().getId();
    }

    @GetMapping("/update/{id2}")
    public String showUpdateAssessmentForm(@PathVariable("id") Integer id, @PathVariable("id2") Integer id2, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("assessment", assessmentService.findById(id2));
        model.addAttribute("studentId", id);
        List<Subject> subjects = subjectService.listAll();  // список всех предметов
        model.addAttribute("subjectsList", subjects);
        return "assessment/assessment-form";
    }

    @GetMapping("/delete/{id2}")
    public String deleteAssessment(@PathVariable("id2") Integer id2, RedirectAttributes ra) {
        assessmentService.delete(id2);
        ra.addFlashAttribute("message", "Assessment deleted");
        return "redirect:/students/assessments/{id}";
    }
}
