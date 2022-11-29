package learning.mvcstudentapplication.db.repository;

import learning.mvcstudentapplication.db.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
