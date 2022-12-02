package learning.mvcstudentapplication.db.repository.security;

import learning.mvcstudentapplication.db.entity.security.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
