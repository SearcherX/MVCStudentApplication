package learning.mvcstudentapplication.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import learning.mvcstudentapplication.db.entity.Assessment;
import learning.mvcstudentapplication.db.entity.Student;
import learning.mvcstudentapplication.db.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public void delete(Integer id) {
        // 1. найти студента для удаления
        Optional<Assessment> deleted = assessmentRepository.findById(id);
        // 2. если такой студент есть, то удалить его
        deleted.ifPresent(assessment -> assessmentRepository.delete(assessment));
    }

    public Map<String, ArrayList<Integer>> getStudentAssessmentMap(int id) {
        List<Assessment> records = listByStudentId(id);
        Map<String, ArrayList<Integer>> assessmentMap = new TreeMap<>();

        for (Assessment record: records) {
            if (!assessmentMap.containsKey(record.getSubject().getSubjectName()))
                assessmentMap.put(record.getSubject().getSubjectName(), new ArrayList<>());
            assessmentMap.get(record.getSubject().getSubjectName()).add(record.getAssessmentValue());
        }

        return assessmentMap;
    }
}
