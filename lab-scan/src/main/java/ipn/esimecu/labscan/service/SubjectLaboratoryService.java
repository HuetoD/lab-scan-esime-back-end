package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.entity.LaboratoryEntity;
import ipn.esimecu.labscan.entity.SubjectEntity;
import ipn.esimecu.labscan.entity.SubjectLaboratoryEntity;
import ipn.esimecu.labscan.repository.SubjectLaboratoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectLaboratoryService {

    private final SubjectLaboratoryRepository subjectLaboratoryRepository;

    @Transactional
    public <AnyCollection extends Collection<SubjectEntity>> void saveWithPersistedSubjects(Map<LaboratoryEntity, AnyCollection> labSubjectsMap, AnyCollection persistedSubjects) {
        final List<SubjectLaboratoryEntity> subjectLabEntities = new ArrayList<>(persistedSubjects.size());
        labSubjectsMap.forEach((lab, subjects) -> {
            subjects.forEach(subject -> {
                SubjectLaboratoryEntity entity = new SubjectLaboratoryEntity();
                entity.setLaboratory(lab);
                entity.setSubject(
                        persistedSubjects.stream()
                                .filter(persisted -> isEqualSubject(persisted, subject))
                                .findFirst()
                                .orElseThrow(() -> new EntityNotFoundException("No se ha creado una asignatura de nombre: " + subject.getCourse().getCourseName()))
                );
                subjectLabEntities.add(entity);
            });
        });

        subjectLaboratoryRepository.saveAll(subjectLabEntities);
    }

    private boolean isEqualSubject(SubjectEntity subject, SubjectEntity another) {
        return subject.getGroup().equals(another.getGroup())
                && subject.getSchedules().equals(another.getSchedules())
                && subject.getCourse().equals(another.getCourse())
                && subject.getSemester().equals(another.getSemester())
                && subject.getTeacher().equals(another.getTeacher());
    }



}
