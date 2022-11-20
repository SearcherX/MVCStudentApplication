package learning.mvcstudentapplication.db.repository;

import learning.mvcstudentapplication.db.entity.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {
}
