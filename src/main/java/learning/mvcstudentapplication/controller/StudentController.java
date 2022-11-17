package learning.mvcstudentapplication.controller;

import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.service.GroupService;
import learning.mvcstudentapplication.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentController {
    private final StudentService service;
    @Autowired
    public GroupService groupService;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/students")
    public String showStudentsList(Model model) {
        List<Student> studentsList = service.listAll();
        model.addAttribute("studentsList", studentsList);
        return "students-list";
    }

    // обработчик на получение формы для добавления студента
    @GetMapping("/students/new")
    public String showNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        List<Group> groups = groupService.listAllGroups();  // список всех групп
        model.addAttribute("groupsList", groups);
        return "student-form";
    }

    // обработчик для сохранения данных о пользователе
    @PostMapping("/students/new")
    public String saveNewStudent(Student student, RedirectAttributes ra) {
        // 1. сохраняем нового студента в БД
        Student saved = service.saveStudent(student);
        // 2. добавить сообщение о том, что студент добавлен
        ra.addFlashAttribute("message",
                "Student " + saved + " saved successfully");
        // 3. выполнить перенаправление
        return "redirect:/students";
    }

    // обработчик для удаления студент
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra) {
        service.deleteStudentById(id);
        ra.addFlashAttribute("message", "Student deleted");
        return "redirect:/students";
    }
}
