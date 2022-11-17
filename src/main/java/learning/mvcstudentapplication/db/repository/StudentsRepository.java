package learning.mvcstudentapplication.db.repository;

import learning.mvcstudentapplication.db.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentsRepository extends CrudRepository<Student, Integer> {

}