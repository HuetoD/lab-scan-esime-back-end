package ipn.esimecu.labscan.services;

import ipn.esimecu.labscan.dto.Data;
import ipn.esimecu.labscan.entity.AttendanceEntity;
import ipn.esimecu.labscan.entity.LaboratoryEntity;
import ipn.esimecu.labscan.entity.ScheduleEntity;
import ipn.esimecu.labscan.entity.StudentEntity;
import ipn.esimecu.labscan.service.XlsxService;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class XlsxServiceTest {

    private final XlsxService xlsxService;

    @Autowired
    public XlsxServiceTest(XlsxService xlsxService) {
        this.xlsxService = xlsxService;
    }

    @Test
    @Order(1)
    public void generateXlsxTest() {
        StudentEntity max = new StudentEntity();
        LaboratoryEntity arturo = new LaboratoryEntity();
        AttendanceEntity alan = new AttendanceEntity();
        Data wil = new Data();

        max.setFullName("Max Aguilar");
        arturo.setLaboratoryId(1);
        max.setPcNumber("2");
        max.setStudentId(14444444);

        wil.setObservation(new AttendanceEntity());
        alan.setObservation("ninguna");

        wil.setLaboratory(arturo);
        wil.setStudent(max);
        wil.setAttendance(alan);

        Assertions.assertDoesNotThrow(() -> xlsxService.generateXlsx(Arrays.asList(wil)));
    }



}