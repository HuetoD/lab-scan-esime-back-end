package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.Data;
import ipn.esimecu.labscan.entity.AttendanceEntity;
import ipn.esimecu.labscan.entity.LaboratoryEntity;
import ipn.esimecu.labscan.entity.StudentEntity;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class XlsxService {

        public void generateXlsx(List<Data> datos) throws IOException {
                // Blank workbook
                XSSFWorkbook workbook = new XSSFWorkbook();
            
                // Creating a blank Excel sheet
                XSSFSheet sheet = workbook.createSheet("student Details");
            
                // Writing data to Object[] using put() method
//Map<String, Object[]> data = new TreeMap<>();
              //  data.put("1", new Object[] {"", "", "", "", "04/09/2023"});
               // data.put("2", new Object[] {"", "", "", "", "20:30-22:00"});
                //data.put("3", new Object[] {"CPU", "ID", "Nombre Completo", "Observaciones", "LAB 1"});
                //data.put("4", new Object[] {3, "20203456434", "Aguilar Aza", "", "X"});
            
                // Iterating over data and writing it to sheet
             //   Set<String> keyset = data.keySet();
               int rownum = 0;
            
                // Header row
                Row headerRow = sheet.createRow(rownum++);
                headerRow.createCell(0).setCellValue("CPU");
                headerRow.createCell(1).setCellValue("ID");
                headerRow.createCell(2).setCellValue("Nombre Completo");
                headerRow.createCell(3).setCellValue("Observacion");
                headerRow.createCell(4).setCellValue("Laboratorio");
            
                // Data rows
                for (Data recorrido : datos) {
                    Row row = sheet.createRow(rownum++);
                    StudentEntity student = recorrido.getStudent();
                    AttendanceEntity attendance = recorrido.getObservation();
                    LaboratoryEntity laboratory = recorrido.getLaboratory();
            
                    row.createCell(0).setCellValue(student.getPcNumber());
                    row.createCell(1).setCellValue(student.getStudentId());
                    row.createCell(2).setCellValue(student.getFullName());
                    row.createCell(3).setCellValue(attendance.getObservation());
                    row.createCell(4).setCellValue(laboratory.getLaboratoryId());
                }
            
                // Try block to check for exceptions
                try {

                        // Writing the workbook
                        FileOutputStream out = new FileOutputStream(
                                new File("gfgcontribute.xlsx"));
                        workbook.write(out);
            
                        // Closing file output connections
                        out.close();
            
                        // Console message for successful execution of
                        // program
                        System.out.println(
                                "gfgcontribute.xlsx written successfully on disk.");
                    }
            
                    // Catch block to handle exceptions
                    catch (Exception e) {
            
                        // Display exceptions along with line number
                        // using printStackTrace() method
                        throw e;
                    }
            }
            
}
