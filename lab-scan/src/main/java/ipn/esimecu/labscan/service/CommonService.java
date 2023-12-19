package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.GroupDTO;
import ipn.esimecu.labscan.dto.LaboratoryDTO;
import ipn.esimecu.labscan.dto.response.SemesterResponse;
import ipn.esimecu.labscan.entity.IdentificationTypeEntity;
import ipn.esimecu.labscan.entity.LaboratoryEntity;
import ipn.esimecu.labscan.entity.SemesterEntity;
import ipn.esimecu.labscan.entity.constant.Day;
import ipn.esimecu.labscan.mapper.LaboratoryMapper;
import ipn.esimecu.labscan.mapper.SemesterMapper;
import ipn.esimecu.labscan.repository.IdentificationTypeRepository;
import ipn.esimecu.labscan.repository.LaboratoryRepository;
import ipn.esimecu.labscan.repository.SemesterRepository;
import ipn.esimecu.labscan.repository.SubjectLaboratoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final SubjectService subjectService;
    private final SemesterRepository semesterRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final IdentificationTypeRepository identificationTypeRepository;
    private final SubjectLaboratoryRepository subjectLaboratoryRepository;
    private final SemesterMapper semesterMapper;
    private final LaboratoryMapper laboratoryMapper;

    @Transactional(readOnly = true)
    public List<SemesterResponse> getSemesters() {
        return semesterMapper.mapAllDTOs(semesterRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<LaboratoryDTO> getLabs() {
        return laboratoryMapper.mapAllDTOs(laboratoryRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<GroupDTO> getGroupsOfTheWeek(String laboratory, int semester) {
        final Set<GroupDTO> uniqueGroups = new HashSet<>();
        List<GroupDTO> groups = Stream.of(Day.values())
                .flatMap(day -> this.getGroups(laboratory, semester, day).stream())
                .distinct()
                .peek(uniqueGroups::add)
                .collect(Collectors.toList());

        groups.removeIf(group -> group.getSubjectLabId() == 0 && uniqueGroups.stream()
                                                                            .anyMatch(unique -> unique.getGroupName().equals(group.getGroupName())
                                                                                                && unique.getSubjectLabId() > 0));

        groups.sort(Comparator.comparing(GroupDTO::getGroupName));

        return groups;
    }

    @Transactional(readOnly = true)
    public List<GroupDTO> getGroups(String laboratory, int semester, LocalDate date) {
        return getGroups(laboratory, semester, Day.of(date));
    }

    @Transactional(readOnly = true)
    public List<GroupDTO> getGroups(String laboratory, int semester, Day day) {
        LaboratoryEntity laboratoryEntity = laboratoryRepository.findByName(laboratory).orElseThrow(EntityNotFoundException::new);
        final List<GroupDTO> groups = subjectService.getGroupsOfLaboratoryFromEsimeCuApi(laboratoryEntity);

        subjectLaboratoryRepository.findGroupLabNamesByDayAndSemester(laboratory, semester, day.value())
                .stream()
                .map(result -> GroupDTO.builder()
                        .subjectLabId(result.getSubjectLabId())
                        .subjectId(result.getSubjectId())
                        .groupName(result.getGroupName().concat(" - ").concat(result.getLabName()))
                        .build())
                .forEach(groupDTO -> {
                    groups.stream()
                          .filter(value -> value.getGroupName().equals(groupDTO.getGroupName()))
                          .findFirst()
                          .ifPresent(found -> {
                            found.setSubjectId(groupDTO.getSubjectId());
                            found.setSubjectLabId(groupDTO.getSubjectLabId());
                          });
                });

        return groups;
    }

    @Transactional(readOnly = true)
    public List<String> loadAllIdentifiers() {
        return identificationTypeRepository.findAll()
                                        .stream()
                                        .map(IdentificationTypeEntity::getIdentificationType)
                                        .collect(Collectors.toList());
    }

    private boolean createGroupsAndTheirCourses(LaboratoryEntity laboratory, int semesterId) {



        return false;
    }

}
