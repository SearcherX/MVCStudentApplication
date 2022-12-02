package learning.mvcstudentapplication.service.security;

import learning.mvcstudentapplication.db.entity.security.Role;
import learning.mvcstudentapplication.db.repository.security.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> listAll() {
        return (List<Role>) roleRepository.findAll();
    }
}
