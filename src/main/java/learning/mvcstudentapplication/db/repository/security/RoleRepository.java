package learning.mvcstudentapplication.db.repository.security;

import learning.mvcstudentapplication.db.entity.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}