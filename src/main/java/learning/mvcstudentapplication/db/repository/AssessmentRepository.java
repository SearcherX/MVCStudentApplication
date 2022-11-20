package learning.mvcstudentapplication.db.repository;

import learning.mvcstudentapplication.db.entity.Assessment;
import org.springframework.data.repository.CrudRepository;

public interface AssessmentRepository extends CrudRepository<Assessment, Integer> {
}
