package learning.mvcstudentapplication.controller.student;

import learning.mvcstudentapplication.controller.filter.AssessmentFilter;
import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.service.AssessmentService;
import learning.mvcstudentapplication.service.GroupService;
import learning.mvcstudentapplication.service.StudentService;
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

    //обработчик на получение списка студентов, учитывая фильтр
    @GetMapping("")
    public String showStudentsList(@RequestParam(required = false, defaultValue = "") String containsFilter, Model model) {
        List<Student> studentsList;

        if (containsFilter != null && !containsFilter.isEmpty())
            studentsList = studentService.findByContains(containsFilter);
        else
            studentsList = studentService.listAll();

        model.addAttribute("studentsList", studentsList);
        model.addAttribute("containsFilter", containsFilter);
        return "student/students-list";
    }

    //обработчик на получение карточки с подробной информации о студенте
    @GetMapping("/details/{id}")
    public String showDetailsCard(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        AssessmentFilter filter = new AssessmentFilter(assessmentService.listByStudentId(id));
        model.addAttribute("assessmentsMap", filter.getAssessmentMap());
        model.addAttribute("avgMap", filter.getAvgMap());
        model.addAttribute("avgAll", filter.getAvgAll());
        return "student/student-info";
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

    // обработчик для сохранения данных о студенте
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student, RedirectAttributes ra,
                              @RequestParam String action) {
        // 1. сохраняем нового студента в БД
        Student saved = studentService.saveStudent(student);
        // 2. добавить сообщение о том, что студент сохранен
        ra.addFlashAttribute("message",
                "Student " + saved + " " + action + "d successfully");
        // 3. выполнить перенаправление
        return "redirect:/students";
    }

    //обработчик на получение формы для обновления студента
    @GetMapping("/update/{id}")
    public String showUpdateStudentForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("student", studentService.findById(id));
        System.out.println(studentService.findById(id));
        List<Group> groups = groupService.listAllGroups();  // список всех групп
        model.addAttribute("groupsList", groups);
        return "student/student-form";
    }

    // обработчик для удаления студента
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra) {
        studentService.deleteStudentById(id);
        ra.addFlashAttribute("message", "Student deleted");
        return "redirect:/students";
    }
}
