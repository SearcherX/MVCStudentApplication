package learning.mvcstudentapplication.service;

import learning.mvcstudentapplication.db.entity.Subject;
import learning.mvcstudentapplication.db.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> listAll() {
        return (List<Subject>)subjectRepository.findAll();
    }
}
