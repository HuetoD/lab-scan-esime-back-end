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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectLaboratoryService {

    private final SubjectLaboratoryRepository subjectLaboratoryRepository;

    @Transactional
    public <AnyCollection extends Collection<SubjectEntity>> List<SubjectLaboratoryEntity> saveWithPersistedSubjects(Map<LaboratoryEntity, AnyCollection> labSubjectsMap, AnyCollection persistedSubjects) {
        final List<SubjectLaboratoryEntity> subjectLabEntities = new ArrayList<>(persistedSubjects.size());
        labSubjectsMap.forEach((lab, subjects) -> {
            subjects.forEach(subject -> {
                SubjectLaboratoryEntity entity = new SubjectLaboratoryEntity();
                entity.setLaboratory(lab);
                entity.setSubject(
                        persistedSubjects.stream()
                                .filter(persisted -> this.isEqualSubject(persisted, subject))
                                .findFirst()
                                .orElseThrow(() -> new EntityNotFoundException("No se ha creado una asignatura de nombre: " + subject.getCourse().getCourseName()))
                );
                subjectLabEntities.add(entity);
            });
        });

        return subjectLaboratoryRepository.saveAll(subjectLabEntities);
    }

    public Optional<SubjectLaboratoryEntity> findById(int id) {
        return this.subjectLaboratoryRepository.findById(id);
    }

    private boolean isEqualSubject(SubjectEntity subject, SubjectEntity another) {
        return subject.getGroup().equals(another.getGroup())
                && subject.getCourse().equals(another.getCourse())
                && subject.getSemester().equals(another.getSemester())
                && subject.getTeacher().equals(another.getTeacher());
    }



}
