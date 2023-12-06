package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.exception.DaeServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Service
@Slf4j
public class DaeService {

    private final String DAE_SERVICE_URL = "https://servicios.dae.ipn.mx/vcred/?h=";

    private HtmlPage page;

    public StudentDTO findStudent(String qrCode) throws DaeServiceException {
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setUseInsecureSSL(true);

            page = webClient.getPage(DAE_SERVICE_URL.concat(qrCode));

            if(getFirstDivByClassName("wrapper") != null)
                throw new DaeServiceException("Sello inexistente");

            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setStudentId(0);
            studentDTO.setStudentFullName(getFirstDivByClassName("nombre").getTextContent());
            studentDTO.setStudentPcNumber("");
            studentDTO.setStudentReportNumber(getFirstDivByClassName("boleta").getTextContent());
            studentDTO.setStudentQrCode(qrCode);

            return studentDTO;
        } catch (FailingHttpStatusCodeException | IOException e) {
            throw new DaeServiceException(e);
        }
    }

    private DomElement getFirstDivByClassName(String name) {
        final List<DomElement> elements = page.getByXPath("//div[contains(@class, '" + name + "')]");
        return elements == null || elements.isEmpty() ? null : elements.get(0);
    }

}
