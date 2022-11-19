package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.service.GroupService;
import learning.mvcstudentapplication.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.Id;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;
    @Autowired
    public GroupService groupService;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("")
    public String showStudentsList(Model model) {
        List<Student> studentsList = service.listAll();
        model.addAttribute("studentsList", studentsList);
        return "student/students-list";
    }

    @GetMapping("/details/{id}")
    public String showDetailsCard(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("student", service.findById(id));
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

    // обработчик для сохранения данных о пользователе
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student, RedirectAttributes ra) {
        // 1. сохраняем нового студента в БД
        Student saved = service.saveStudent(student);
        // 2. добавить сообщение о том, что студент добавлен
        ra.addFlashAttribute("message",
                "Student " + saved + " saved successfully");
        // 3. выполнить перенаправление
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public String showUpdateStudentForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("student", service.findById(id));
        System.out.println(service.findById(id));
        List<Group> groups = groupService.listAllGroups();  // список всех групп
        model.addAttribute("groupsList", groups);
        return "student/student-form";
    }

    // обработчик для удаления студент
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra) {
        service.deleteStudentById(id);
        ra.addFlashAttribute("message", "Student deleted");
        return "redirect:/students";
    }
}
