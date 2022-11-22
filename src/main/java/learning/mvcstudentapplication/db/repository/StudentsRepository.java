package learning.mvcstudentapplication.db.repository;

import learning.mvcstudentapplication.db.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentsRepository extends CrudRepository<Student, Integer>, JpaRepository<Student, Integer> {
    //JPQL-фильтр для студентов
    @Query("select s from Student s " +
            "where lower(s.firstName) like lower(concat('%', :match,'%'))" +
            "or lower(s.lastName) like lower(concat('%', :match,'%'))")
    List<Student> getFilteredStudents(@Param("match") String match);
}