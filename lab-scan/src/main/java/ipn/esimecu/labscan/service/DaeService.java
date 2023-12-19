package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.StudentBaseDTO;
import ipn.esimecu.labscan.exception.DaeServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Service
@Slf4j
public class DaeService {

    private final String DAE_SERVICE_URL = "https://servicios.dae.ipn.mx/vcred/?h=";

    private HtmlPage page;

    public StudentBaseDTO findStudent(String qrCode) throws DaeServiceException {
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setUseInsecureSSL(true);

            page = webClient.getPage(DAE_SERVICE_URL.concat(qrCode));

            final Function<String, DaeServiceException> daeExceptionFunc = message -> new DaeServiceException(message,
                    false);

            if (getFirstDivByClassName("wrapper").orElse(null) != null)
                throw daeExceptionFunc.apply("Sello inexistente");

            StudentBaseDTO studentBaseDTO = new StudentBaseDTO();
            studentBaseDTO.setStudentId(0);
            studentBaseDTO.setStudentFullName(
                    getFirstDivByClassName("nombre").orElseThrow(() -> daeExceptionFunc.apply("Nombre inexsitente"))
                            .getTextContent());
            studentBaseDTO.setStudentPcNumber("");
            studentBaseDTO.setStudentReportNumber(
                    getFirstDivByClassName("boleta").orElseThrow(() -> daeExceptionFunc.apply("Boleta inexistente"))
                            .getTextContent());
            studentBaseDTO.setStudentQrCode(qrCode);

            return studentBaseDTO;
        } catch (FailingHttpStatusCodeException | IOException e) {
            throw new DaeServiceException(e);
        }
    }

    private Optional<DomElement> getFirstDivByClassName(String name) {
        final List<DomElement> elements = page.getByXPath("//div[contains(@class, '" + name + "')]");
        return Optional.ofNullable(elements == null || elements.isEmpty() ? null : elements.get(0));
    }

}
