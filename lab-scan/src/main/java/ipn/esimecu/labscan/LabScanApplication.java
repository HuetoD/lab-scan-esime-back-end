package ipn.esimecu.labscan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LabScanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabScanApplication.class, args);
	}

}
