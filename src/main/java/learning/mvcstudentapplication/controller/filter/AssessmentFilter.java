package learning.mvcstudentapplication.controller.filter;

import learning.mvcstudentapplication.db.entity.Assessment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AssessmentFilter {
    //словарь из предмета и списка оценок
    private final Map<String, ArrayList<Integer>> assessmentMap = new TreeMap<>();
    //словарь из предмета и среднего балла
    private final Map<String, Double> avgMap = new TreeMap<>();
    //средний балл по всем предметам
    private double avgAll = 0;

    public Map<String, ArrayList<Integer>> getAssessmentMap() {
        return assessmentMap;
    }

    public Map<String, Double> getAvgMap() {
        return avgMap;
    }

    public double getAvgAll() {
        return avgAll;
    }

    public AssessmentFilter(List<Assessment> records) {
        //обработать список объектов Assessment
        for (Assessment record: records) {
            //если предмета нет в словаре, то добавить его с пустым массивом оценок
            if (!assessmentMap.containsKey(record.getSubject().getSubjectName()))
                assessmentMap.put(record.getSubject().getSubjectName(), new ArrayList<>());
            //добавить оценку в конец массива
            assessmentMap.get(record.getSubject().getSubjectName()).add(record.getAssessmentValue());
        }

        //создать словарь из предмета-среднего балла
        for (Map.Entry<String, ArrayList<Integer>> pair: assessmentMap.entrySet()) {
            double avg = 0;
            if (pair.getValue().size() == 0) continue;
            for (Integer assessmentValue: pair.getValue()) {
                avg += assessmentValue;
            }
            avg /= pair.getValue().size();
            avgMap.put(pair.getKey(), round(avg, 2));
            avgAll += avg;
        }
        avgAll = round(avgAll / avgMap.size(), 2);

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String listToString(List<Integer> values) {
        StringBuilder str = new StringBuilder();
        for (Integer val: values) {
            str.append(val).append(", ");
        }
        str.delete(str.length() - 2, str.length() - 1);
        return str.toString();
    }
}
