package learning.mvcstudentapplication.db.repository;

import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer>, JpaRepository<Group, Integer> {
    //JPQL-фильтр для групп
    @Query("select g from Group g " +
            "where lower(g.groupName) like lower(concat('%', :match,'%'))")
    List<Group> getFilteredGroups(@Param("match") String match);
}
