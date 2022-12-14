package learning.mvcstudentapplication.service;

import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.db.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentsRepository studentsRepository;
    public List<Student> listAll() {
        return (List<Student>) studentsRepository.findAll();
    }

    public Student findById(int id) {
        return studentsRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    // сохранить студента в БД
    public Student saveStudent(Student student) {
        return studentsRepository.save(student);
    }

    // удалить студента по id
    public void deleteStudentById(Integer id) {
        // 1. найти студента для удаления
        Optional<Student> deleted = studentsRepository.findById(id);
        // 2. если такой студент есть, то удалить его
        deleted.ifPresent(student -> studentsRepository.delete(student));
    }

    //фильтр
    public List<Student> findByContains(String match) {
        return studentsRepository.getFilteredStudents(match);
    }

}
