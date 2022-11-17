package learning.mvcstudentapplication.db.repository;

import learning.mvcstudentapplication.db.entity.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Integer> {
}
