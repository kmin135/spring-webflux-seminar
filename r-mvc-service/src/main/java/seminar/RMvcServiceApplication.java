package seminar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class RMvcServiceApplication {
	private RestTemplate rt = new RestTemplate();
	
	@GetMapping("/service")
	public String service(String idx) {
		String body = rt.getForObject("http://localhost:8081/slow-api?idx={idx}", String.class, idx);
		return idx + " / " + body;
	}

	public static void main(String[] args) {
		SpringApplication.run(RMvcServiceApplication.class, args);
	}

}
