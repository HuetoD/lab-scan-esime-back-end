package ipn.esimecu.labscan.services;

import ipn.esimecu.labscan.dto.response.EsimeCuTypeEnumResponse;
import ipn.esimecu.labscan.dto.response.EventGroupEsimecuResponse;
import ipn.esimecu.labscan.entity.CourseEntity;
import ipn.esimecu.labscan.entity.LaboratoryEntity;
import ipn.esimecu.labscan.entity.SubjectEntity;
import ipn.esimecu.labscan.entity.TeacherEntity;
import ipn.esimecu.labscan.repository.LaboratoryRepository;
import ipn.esimecu.labscan.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class SubjectServiceTest {

    private final SubjectService subjectService;

    private final LaboratoryRepository laboratoryRepository;

    @Autowired
    public SubjectServiceTest(SubjectService subjectService, LaboratoryRepository laboratoryRepository) {
        this.subjectService = subjectService;
        this.laboratoryRepository = laboratoryRepository;
    }

    @Test
    @Order(1)
    public void getTypesOfEsimeCuApiTest() {
        Assertions.assertDoesNotThrow(() -> subjectService.getTypesOfEsimeCuApi());
    }

    @Test
    @Order(2)
    public void eventEsimeCuApiNonEmptyTypeListTest() {
        Assertions.assertEquals(false, subjectService.getTypesOfEsimeCuApi().isEmpty());
    }

    @Test
    @Order(3)
    public void eventEsimeCuApiThreeTypesTest() {
        final List<EsimeCuTypeEnumResponse> expected = Arrays.asList(EsimeCuTypeEnumResponse.values());
        Assertions.assertEquals(expected, subjectService.getTypesOfEsimeCuApi());
    }

    @Test
    @Order(4)
    public void filteringGroupsOfEventEsimeCuApiTest() {
        Assertions.assertEquals(false,  subjectService.filterGroupsOfEsimeCuApi().isEmpty());
    }

    @Test
    @Order(5)
    public void containsAsLeastOneTeacherNameTest() {
        final Map<String, List<EventGroupEsimecuResponse>> responses = subjectService.filterGroupsOfEsimeCuApi();
        Assertions.assertEquals(responses.keySet().size(), responses.values()
                                                                    .stream()
                                                                    .filter(list -> !list.stream()
                                                                    .filter(response -> !response.getTeacherFullName()
                                                                                                .equals(subjectService.WITHOUT_TEACHER_RESPONSE_MESSAGE))
                                                                                                .collect(Collectors.toList())
                                                                                                .isEmpty())
                                                                    .collect(Collectors.toList())
                                                                    .size());
    }

    @Test
    @Order(6)
    public void findISWCourseInFilteredGroupsOfEsimeCuApiTest() {
        Assertions.assertDoesNotThrow(() -> subjectService.getSublistOfSubjectName("ARQUITECTURA DE COMPUTADORAS"));
    }

    @Test
    @Order(7)
    public void createOrFindCourseTest() {
        final String subjectTest = "ARQUITECTURA DE COMPUTADORAS";
        final CourseEntity expected = new CourseEntity();
        expected.setCourseId(2);
        expected.setCourseName(subjectTest);
        Assertions.assertEquals(expected, subjectService.findCourseOrCreate(subjectService.getSublistOfSubjectName(subjectTest)));
    }

    @Test
    @Order(8)
    public void createOrFindTeacherTest() {
        final String groupNameTest = "6CV15";
        final String subjectTest = "INGENIERA DE SOFTWARE";
        final TeacherEntity expected = new TeacherEntity();
        expected.setTeacherId(1);
        expected.setFullName("Martinez Corona Eduardo");
        Assertions.assertEquals(expected, subjectService.findTeacherOrCreate(subjectService.getSublistOfSubjectName(subjectTest), groupNameTest));
    }

    @Test
    @Order(9)
    public void createSubjectDoesNotThrowTest() {
        final int semester = 24;
        AtomicReference<LaboratoryEntity> laboratory = new AtomicReference<>(null);
        AtomicReference<SubjectEntity> subjectRef = new AtomicReference<>(null);
        Assertions.assertDoesNotThrow(() -> laboratory.set(laboratoryRepository.findByName("Lab. Computación 2").orElseThrow(EntityNotFoundException::new)));
        Assertions.assertDoesNotThrow(() -> subjectRef.set(subjectService.create(semester, laboratory.get(), "6CV15")));
        Assertions.assertEquals("INGENIERA DE SOFTWARE", subjectRef.get().getCourse().getCourseName());
        log.info(subjectRef.get().getSchedules().toString());
    }
}
