package learning.mvcstudentapplication.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import learning.mvcstudentapplication.db.entity.Assessment;
import learning.mvcstudentapplication.db.entity.Group;
import learning.mvcstudentapplication.db.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {
    @Autowired
    private AssessmentRepository assessmentRepository;

    public List<Assessment> listAll() {
        return (List<Assessment>)assessmentRepository.findAll();
    }

    public List<Assessment> listByStudentId(int id) {
        return ((List<Assessment>)assessmentRepository.findAll())
                .stream()
                .filter(s -> s.getStudent().getId() == id)
                .toList();
    }

    public Assessment findById(int id) {
        return assessmentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Assessment save(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }
}
