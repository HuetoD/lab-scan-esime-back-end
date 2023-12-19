package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.GroupDTO;
import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.dto.response.EsimeCuTypeEnumResponse;
import ipn.esimecu.labscan.dto.response.EventGroupEsimecuResponse;
import ipn.esimecu.labscan.entity.CourseEntity;
import ipn.esimecu.labscan.entity.GroupEntity;
import ipn.esimecu.labscan.entity.LaboratoryEntity;
import ipn.esimecu.labscan.entity.ScheduleEntity;
import ipn.esimecu.labscan.entity.SemesterEntity;
import ipn.esimecu.labscan.entity.SubjectEntity;
import ipn.esimecu.labscan.entity.TeacherEntity;
import ipn.esimecu.labscan.entity.constant.Day;
import ipn.esimecu.labscan.repository.CourseRepository;
import ipn.esimecu.labscan.repository.GroupRepository;
import ipn.esimecu.labscan.repository.LaboratoryRepository;
import ipn.esimecu.labscan.repository.SemesterRepository;
import ipn.esimecu.labscan.repository.SubjectRepository;
import ipn.esimecu.labscan.repository.TeacherRepository;
import jakarta.el.PropertyNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ipn.esimecu.labscan.dto.response.EsimeCuTypeEnumResponse.T;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService implements InitializingBean {

    private final String ESIME_CU_API = "https://www.eventos.esimecu.ipn.mx/get-grupos/-/ic/";

    public final String WITHOUT_TEACHER_RESPONSE_MESSAGE = "-- Sin Asignar --";

    public final String WITHOUT_CLASS = "-";

    private final SubjectLaboratoryService subjectLaboratoryService;

    private final SubjectRepository subjectRepository;

    private final CourseRepository courseRepository;

    private final TeacherRepository teacherRepository;

    private final GroupRepository groupRepository;

    private final SemesterRepository semesterRepository;

    private final LaboratoryRepository laboratoryRepository;

    private SemesterEntity currentSemester = null;
    private RestTemplate restTemplate;

    @Getter
    private Map<String, List<EventGroupEsimecuResponse>> temporalGroups;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.currentSemester = this.findCurrentSemester();
        this.restTemplate = new RestTemplate();
        this.temporalGroups = this.filterGroupsOfEsimeCuApi();
    }

    public ResponseEntity<List<EventGroupEsimecuResponse>> callEsimeCuApi() {
        return restTemplate.exchange(ESIME_CU_API.concat(String.valueOf(this.currentSemester.getSemesterId())),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    public List<EsimeCuTypeEnumResponse> getTypesOfEsimeCuApi() {
        return getBodyOfEsimeCuApiAndRequireNotNull()
                            .stream()
                            .map(EventGroupEsimecuResponse::getType)
                            .distinct()
                            .collect(Collectors.toList());
    }

    public Optional<SubjectEntity> findById(int subjectId) {
        return subjectRepository.findById(subjectId);
    }

    @Transactional
    public Map<String, List<EventGroupEsimecuResponse>> filterGroupsOfEsimeCuApi() {
        Map<String, List<EventGroupEsimecuResponse>> filteredGroups = getBodyOfEsimeCuApiAndRequireNotNull()
                                                                                    .stream()
                                                                                    .collect(Collectors.groupingBy(EventGroupEsimecuResponse::getSubjectName));

        this.saveGroupsOrRetrieve(filteredGroups.values()
                                                .stream()
                                                .flatMap(Collection::parallelStream)
                                                .map(EventGroupEsimecuResponse::getGroupName)
                                                .distinct()
                                                .sorted()
                                                .collect(Collectors.toList()));
        return filteredGroups;
    }

    @Transactional
    public List<SubjectEntity> saveSubjects(StudentDTO request) {
        List<LaboratoryEntity> labsFound = this.findLaboratoriesOf(request);
        List<SubjectEntity> subjects = new ArrayList<>();
        Set<SubjectEntity> newSubjects = new HashSet<>();
        Map<LaboratoryEntity, List<SubjectEntity>> labSubjectsMap = new HashMap<>();
        for(final var entry : request.getGroups().entrySet()) {
            for(final GroupDTO groupDTO : entry.getValue()) {
                System.out.println("ID: " + groupDTO.getSubjectId());
                Optional<SubjectEntity> subject = this.findById(groupDTO.getSubjectId());
                if(subject.isPresent()) {
                    subjects.add(subject.get());
                } else {
                    LaboratoryEntity laboratory = labsFound.stream()
                            .filter(lab -> lab.getName().equals(entry.getKey()))
                            .findAny()
                            .orElseThrow(() -> new EntityNotFoundException("No se ha creado el " + entry.getKey()));

                    final SubjectEntity newSubject = this.create(request.getSemesterId(), laboratory, groupDTO.getGroupName());

                    if(!labSubjectsMap.containsKey(laboratory))
                        labSubjectsMap.put(laboratory, new ArrayList<>());

                    labSubjectsMap.get(laboratory).add(newSubject);
                    newSubjects.add(newSubject);
                }
            }
        }

        List<SubjectEntity> newSubjectsSaved = saveAll(newSubjects);
        subjectLaboratoryService.saveWithPersistedSubjects(labSubjectsMap, newSubjectsSaved);
        subjects.addAll(newSubjectsSaved);
        return subjects;
    }

    @Transactional
    public SubjectEntity create(int semester, LaboratoryEntity laboratory, String groupName) {
        if(this.currentSemester.getSemesterId() != semester)
            this.currentSemester = this.findSemesterById(semester);

        GroupEntity groupEntity = groupRepository.findByName(groupName)
                                                .orElseThrow(() -> new EntityNotFoundException("El grupo: " + groupName + ", aun no se ha creado"));

        final List<EventGroupEsimecuResponse> temporalGroups = new ArrayList<>();

        return this.findEntriesOfLaboratoryAndGroupFromTemporalGroups(laboratory, groupName)
                .entrySet()
                .stream()
                .peek(entry -> temporalGroups.addAll(entry.getValue()))
                .flatMap(entry -> entry.getValue().stream())
                .filter(eventGroupResponse -> !eventGroupResponse.getTeacherFullName().equals(WITHOUT_TEACHER_RESPONSE_MESSAGE))
                .findFirst()
                .map(foundResponse -> {
                    SubjectEntity subject = new SubjectEntity();
                    subject.setGroup(groupEntity);
                    subject.setCourse(findCourseOrCreate(temporalGroups));
                    subject.setSchedules(this.createSchedules(foundResponse, subject));
                    subject.setTeacher(findTeacherOrCreate(temporalGroups, foundResponse.getGroupName()));
                    subject.setSemester(this.currentSemester);
                    return subject;
                })
                .orElseThrow();
    }

    public List<SubjectEntity> saveAll(Collection<SubjectEntity> subjects) {
        return subjectRepository.saveAll(subjects);
    }

    @Transactional(readOnly = true)
    public SemesterEntity findCurrentSemester() {
        return semesterRepository.findOneBetween(LocalDate.now()).orElseThrow(() -> new EntityNotFoundException("No existe un semestre que este entre la fecha del dia de hoy"));
    }

    @Transactional(readOnly = true)
    public SemesterEntity findSemesterById(int semesterId) {
        return semesterRepository.findById(semesterId).orElseThrow(() -> new EntityNotFoundException("No existe un semestre con id: ".concat(String.valueOf(semesterId))));
    }

    @Transactional(readOnly = true)
    public long countAllCourses() {
        return courseRepository.count();
    }

    @Transactional(readOnly = true)
    public boolean existsCourseByName(String courseName) {
       return courseRepository.existsByCourseName(courseName);
    }
    @Transactional
    public CourseEntity findCourseOrCreate(List<EventGroupEsimecuResponse> groups) {
        return findCourseOrCreate(findFirstGroupResponseOfType(retrieveSublistFromAnyValueOf(this.temporalGroups, groups), T));
    }

    @Transactional
    public CourseEntity findCourseOrCreate(EventGroupEsimecuResponse groupResponse) {
        return courseRepository.findByCourseName(groupResponse.getSubjectName())
                .orElseGet(() -> {
                    CourseEntity newCourse = new CourseEntity();
                    newCourse.setCourseName(groupResponse.getSubjectName());
                    return courseRepository.save(newCourse);
                });
    }

    @Transactional
    public List<GroupEntity> saveGroupsOrRetrieve(List<String> names) {
        names = names.stream()
                    .distinct()
                    .collect(Collectors.toList());

        List<GroupEntity> founds = groupRepository.findByNameIn(names);

        names.removeIf(name -> founds.stream().anyMatch(group -> group.getName().equals(name)));

        if(names.isEmpty())
            return founds;

        List<GroupEntity> newGroups = groupRepository.saveAll(names.stream().map(name -> {
            GroupEntity newGroup = new GroupEntity();
            newGroup.setName(name);
            newGroup.setShift(name.charAt(2));
            return newGroup;

        }).collect(Collectors.toList()));

        founds.addAll(newGroups);

        return founds;
    }

    @Transactional
    public TeacherEntity findTeacherOrCreate(List<EventGroupEsimecuResponse> groups, String group) {
        final EventGroupEsimecuResponse groupResponse = findFirstGroupResponseFiltered(groups, response -> !response.getTeacherFullName()
                                                                                                                    .equals(WITHOUT_TEACHER_RESPONSE_MESSAGE)
                                                                                                            && response.getGroupName()
                                                                                                                    .equals(group));
        return teacherRepository.findByFullName(groupResponse.getTeacherFullName())
                .orElseGet(() -> {
                    TeacherEntity newTeacher = new TeacherEntity();
                    newTeacher.setFullName(groupResponse.getTeacherFullName());
                    return teacherRepository.save(newTeacher);
                });
    }

    @Transactional(readOnly = true)
    public List<LaboratoryEntity> findLaboratoriesOf(StudentDTO request) {
        return laboratoryRepository.findByNameIn(request.getGroups().keySet());
    }

    public List<EventGroupEsimecuResponse> getSublistOfSubjectName(String subjectName) {
        return getSublistOfSubjectName(this.filterGroupsOfEsimeCuApi(), subjectName);
    }

    public List<EventGroupEsimecuResponse> getSublistOfSubjectName(Map<String, List<EventGroupEsimecuResponse>> groups, String subjectName) {
        return groups
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(subjectName))
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
    }

    public List<ScheduleEntity> createSchedules(EventGroupEsimecuResponse group, SubjectEntity subject) {
        final List<Day> days = new ArrayList<>(Day.values().length);
        final List<EventGroupEsimecuResponse> filteredGroups = this.filterEventGroupSublist(
                                                                this.retrieveSublistFromAnyValueOf(this.temporalGroups, Collections.singletonList(group)),
                                                                find -> find.getGroupName().equals(subject.getGroup().getName())
                                                    );

        final Function<String, Boolean> function = value -> value != null && !value.isEmpty() && !value.equals(WITHOUT_CLASS);

        final Consumer<EventGroupEsimecuResponse> consumer = eventGroup -> {

            if(function.apply(eventGroup.getMonday()))
                days.add(Day.MONDAY);

            if(function.apply(eventGroup.getTuesday()))
                days.add(Day.TUESDAY);

            if(function.apply(eventGroup.getWednesday()))
                days.add(Day.WEDNESDAY);

            if(function.apply(eventGroup.getThursday()))
                days.add(Day.THURSDAY);

            if(function.apply(eventGroup.getFriday()))
                days.add(Day.FRIDAY);
        };

        filteredGroups.forEach(consumer);

        return days.stream()
                .distinct()
                .sorted()
                .map(day -> {
                    ScheduleEntity schedule = new ScheduleEntity();
                    schedule.setSubject(subject);
                    schedule.setDay(day);
                    return schedule;
                })
                .collect(Collectors.toList());
    }

    public Map<String, List<EventGroupEsimecuResponse>> findEntriesOfLaboratoryAndGroupFromTemporalGroups(LaboratoryEntity laboratory, String groupName) throws NoSuchElementException {
        final var entries = this.findEntriesOfLaboratoryAndGroup(this.temporalGroups, laboratory, Optional.ofNullable(groupName));
        if(entries == null || entries.isEmpty())
            throw new NoSuchElementException(
                    "No se encontraron asignaturas del semestre "
                            + currentSemester.getSemesterId()
                            + ", para el " + laboratory.getName()
                            + " y con grupo en el " + groupName
                            + " :: Inicio | fin del semestre: " + "[" + currentSemester.getStartDate() + " | " + currentSemester.getEndDate() + "]");
        return entries;
    }

    public EventGroupEsimecuResponse findFirstGroupResponseOfType(List<EventGroupEsimecuResponse> groups, EsimeCuTypeEnumResponse type) {
        return findFirstGroupResponseFiltered(groups, group -> group.getType().equals(type));
    }

    public Optional<EventGroupEsimecuResponse> findFirstOptionalGroupResponseFiltered(List<EventGroupEsimecuResponse> groups, Predicate<? super EventGroupEsimecuResponse> predicate) {
        return groups.stream()
                .filter(predicate)
                .findFirst();
    }

    public EventGroupEsimecuResponse findFirstGroupResponseFiltered(List<EventGroupEsimecuResponse> groups, Predicate<? super EventGroupEsimecuResponse> predicate) throws NoSuchElementException {
        return this.findFirstOptionalGroupResponseFiltered(groups, predicate)
                .orElseThrow(() -> new NoSuchElementException("No se pudo aplicar el predicate usando la lista: " + groups.toString()));
    }

    public List<EventGroupEsimecuResponse> filterEventGroupSublist(List<EventGroupEsimecuResponse> groups, Predicate<? super EventGroupEsimecuResponse> predicate) {
            return groups.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
    }

    public Map<String, List<EventGroupEsimecuResponse>> findEntriesOfLaboratoryAndGroup(Map<String, List<EventGroupEsimecuResponse>> responses, LaboratoryEntity laboratory, Optional<String> groupName) {
        return responses.values()
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(response -> checkIfContainsLabNameOnWeek(response, laboratory.getName())
                                    && (groupName.isEmpty() || response.getGroupName().equals(groupName.get())))
                        .collect(Collectors.groupingBy(EventGroupEsimecuResponse::getSubjectName));
    }

    public boolean checkIfContainsLabNameOnWeek(EventGroupEsimecuResponse response, String labName) {
        return response.getMonday().equals(labName)
                || response.getTuesday().equals(labName)
                || response.getWednesday().equals(labName)
                || response.getThursday().equals(labName)
                || response.getFriday().equals(labName);
    }

    public List<EventGroupEsimecuResponse> retrieveSublistFromAnyValueOf(Map<String, List<EventGroupEsimecuResponse>> all, List<EventGroupEsimecuResponse> sublist) {
        List<EventGroupEsimecuResponse> retrieves = new ArrayList<>();
        for(final var entry : all.entrySet())
            if(entry.getValue().stream().anyMatch(sublist::contains))
                retrieves.addAll(entry.getValue());

        if(retrieves.isEmpty())
            throw new PropertyNotFoundException("Naaah, error");
        return retrieves;
    }

    public Stream<EventGroupEsimecuResponse> getFilteredCoursesStream(Map<String, List<EventGroupEsimecuResponse>> all) {
        return all.values()
                .stream()
                .map(value -> findFirstOptionalGroupResponseFiltered(value, res -> res.getType().equals(EsimeCuTypeEnumResponse.T)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparing(EventGroupEsimecuResponse::getGroupName))
                .distinct();
    }

    public List<GroupDTO> getGroupsOfLaboratoryFromEsimeCuApi(LaboratoryEntity laboratory) {
        return findEntriesOfLaboratoryAndGroupFromTemporalGroups(laboratory, null)
                .values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(EventGroupEsimecuResponse::getGroupName))
                .map(result -> GroupDTO.builder()
                        .subjectLabId(0)
                        .subjectId(0)
                        .groupName(result.getGroupName() + " - " + (laboratory.getName()))
                        .build())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<EventGroupEsimecuResponse>  getBodyOfEsimeCuApiAndRequireNotNull() {
        return Objects.requireNonNull(callEsimeCuApi().getBody(), "Calling the esime cu API, body returned is null");
    }

}