package learning.mvcstudentapplication.service;

import learning.mvcstudentapplication.db.entity.Assessment;
import learning.mvcstudentapplication.db.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService {
    @Autowired
    private AssessmentRepository assessmentRepository;

    public List<Assessment> listAll() {
        return (List<Assessment>)assessmentRepository.findAll();
    }

    //вернуть список оценок для определенного студента
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

    public void delete(Integer id) {
        // 1. найти оценку для удаления
        Optional<Assessment> deleted = assessmentRepository.findById(id);
        // 2. если такая оценка есть, то удалить её
        deleted.ifPresent(assessment -> assessmentRepository.delete(assessment));
    }

}
