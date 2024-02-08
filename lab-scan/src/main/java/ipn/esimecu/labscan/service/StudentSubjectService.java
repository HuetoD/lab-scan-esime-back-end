package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.entity.StudentEntity;
import ipn.esimecu.labscan.entity.StudentSubjectEntity;
import ipn.esimecu.labscan.entity.SubjectEntity;
import ipn.esimecu.labscan.entity.SubjectLaboratoryEntity;
import ipn.esimecu.labscan.repository.StudentSubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentSubjectService {

    private final StudentSubjectRepository studentSubjectRepository;

    @Autowired
    public StudentSubjectService(StudentSubjectRepository studentSubjectRepository) {
        this.studentSubjectRepository = studentSubjectRepository;
    }

    @Transactional
    public List<StudentSubjectEntity> save(StudentEntity student, List<SubjectLaboratoryEntity> subjects) {
        System.out.println("Save student | labs: " + subjects);
        return studentSubjectRepository.saveAll(
                subjects.stream()
                        .map(subject -> {
                            StudentSubjectEntity entity = new StudentSubjectEntity();
                            entity.setSubjectLab(subject);
                            entity.setStudent(student);
                            return entity;
                        })
                        .collect(Collectors.toList())
                );
    }

    @Transactional
    public void deleteAll(List<StudentSubjectEntity> list) {
        studentSubjectRepository.deleteAll(list);
    }

}
