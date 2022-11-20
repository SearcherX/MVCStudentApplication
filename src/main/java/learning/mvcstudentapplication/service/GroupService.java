package learning.mvcstudentapplication.service;

import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    // репозиторий групп
    @Autowired
    private GroupRepository groupRepository;
    // получение списка всех групп
    public List<Group> listAllGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    public Group findById(int id) {
        return groupRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroupById(Integer id) {
        // 1. найти студента для удаления
        Optional<Group> deleted = groupRepository.findById(id);
        // 2. если такой студент есть, то удалить его
        deleted.ifPresent(group -> groupRepository.delete(group));
    }
}
