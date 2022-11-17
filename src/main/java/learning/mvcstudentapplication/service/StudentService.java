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
    private StudentsRepository repository;
    public List<Student> listAll() {
        return (List<Student>)repository.findAll();
    }

    // сохранить студента в БД
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    // удаления студента по id
    public void deleteStudentById(Integer id) {
        // 1. найти студента для удаления
        Optional<Student> deleted = repository.findById(id);
        // 2. если такой студент есть, то удалить его
        deleted.ifPresent(student -> repository.delete(student));
    }
}
