package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.controller.filter.AssessmentFilter;
import learning.mvcstudentapplication.db.entity.Assessment;
import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Student;
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
@RequestMapping("/students")
public class StudentController {
    @Autowired
    public StudentService studentService;
    @Autowired
    public GroupService groupService;

    @Autowired
    public AssessmentService assessmentService;

    @Autowired
    public SubjectService subjectService;

    @GetMapping("")
    public String showStudentsList(Model model) {
        List<Student> studentsList = studentService.listAll();
        model.addAttribute("studentsList", studentsList);
        return "student/students-list";
    }

    @GetMapping("/details/{id}")
    public String showDetailsCard(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        AssessmentFilter filter = new AssessmentFilter(assessmentService.listByStudentId(id));
        model.addAttribute("assessmentsMap", filter.getAssessmentMap());
        model.addAttribute("avgMap", filter.getAvgMap());
        model.addAttribute("avgAll", filter.getAvgAll());
        return "student/student-info";
    }

    @GetMapping("/assessments/{id}")
    public String showAssessmentList(Model model, @PathVariable("id") Integer id) {
        System.out.println("test");
        List<Assessment> assessmentList = assessmentService.listByStudentId(id);
        model.addAttribute("assessmentList", assessmentList);
        model.addAttribute("student", studentService.findById(id));
        return "assessment/assessments-list";
    }

    @GetMapping("/assessments/{id}/new")
    public String showAssessmentForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("assessment", new Assessment());
        model.addAttribute("studentId", id);
        model.addAttribute("student", studentService.findById(id));
        List<Subject> subjects = subjectService.listAll();  // список всех групп
        model.addAttribute("subjectsList", subjects);
        return "assessment/assessment-form";
    }

    @PostMapping("/assessments/{id}/save")
    public String saveAssessment(@ModelAttribute("assessment") Assessment assessment, RedirectAttributes ra) {
        // 1. сохраняем новую оценку в БД
        Assessment saved = assessmentService.save(assessment);
        // 2. добавить сообщение о том, что оценка добавлена
        ra.addFlashAttribute("message",
                "Assessment " + saved.getAssessmentValue() + " on subject " +
                        saved.getSubject() + " saved successfully");
        // 3. выполнить перенаправление
        return "redirect:/students/assessments/" + assessment.getStudent().getId();
    }

    @GetMapping("/assessments/{id}/delete/{id2}")
    public String deleteAssessment(@PathVariable("id") Integer id, @PathVariable("id2") Integer id2, RedirectAttributes ra) {
        assessmentService.delete(id2);
        ra.addFlashAttribute("message", "Assessment deleted");
        return "redirect:/students/assessments/{id}";
    }

    // обработчик на получение формы для добавления студента
    @GetMapping("/new")
    public String showNewStudentForm(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("student", new Student());
        List<Group> groups = groupService.listAllGroups();  // список всех групп
        model.addAttribute("groupsList", groups);
        return "student/student-form";
    }

    // обработчик для сохранения данных о пользователе
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student, RedirectAttributes ra) {
        // 1. сохраняем нового студента в БД
        Student saved = studentService.saveStudent(student);
        // 2. добавить сообщение о том, что студент добавлен
        ra.addFlashAttribute("message",
                "Student " + saved + " saved successfully");
        // 3. выполнить перенаправление
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public String showUpdateStudentForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("student", studentService.findById(id));
        System.out.println(studentService.findById(id));
        List<Group> groups = groupService.listAllGroups();  // список всех групп
        model.addAttribute("groupsList", groups);
        return "student/student-form";
    }

    // обработчик для удаления студент
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra) {
        studentService.deleteStudentById(id);
        ra.addFlashAttribute("message", "Student deleted");
        return "redirect:/students";
    }
}
