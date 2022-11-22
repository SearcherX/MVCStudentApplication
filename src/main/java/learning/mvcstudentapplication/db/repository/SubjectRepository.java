package learning.mvcstudentapplication.db.repository;

import learning.mvcstudentapplication.db.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Integer>, JpaRepository<Subject, Integer> {
    //JPQL-фильтр для предметов
    @Query("select s from Subject s " +
            "where lower(s.subjectName) like lower(concat('%', :match,'%'))")
    List<Subject> getFilteredSubjects(@Param("match") String match);
}
