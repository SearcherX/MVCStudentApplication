package learning.mvcstudentapplication.controller.filter;

import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentNameFilter {

    private String match = "";   // строка фильтра
    public String getMatch() {
        return match;
    }
    public void setMatch(String match) {
        this.match = match;
    }

    public List<Student> getFilteredStudents(StudentService service) {
        // фильтрация студентов на основе включения match в имя или фамилию
        return service.findByContains(match);
    }
}
