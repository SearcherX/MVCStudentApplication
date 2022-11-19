package learning.mvcstudentapplication.service;

import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.db.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    // репозиторий групп
    @Autowired
    private GroupRepository repository;
    // получение списка всех групп
    public List<Group> listAllGroups() {
        return (List<Group>)repository.findAll();
    }

    public Group findById(int id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Group saveGroup(Group group) {
        return repository.save(group);
    }

    public void deleteGroupById(Integer id) {
        // 1. найти студента для удаления
        Optional<Group> deleted = repository.findById(id);
        // 2. если такой студент есть, то удалить его
        deleted.ifPresent(group -> repository.delete(group));
    }
}
