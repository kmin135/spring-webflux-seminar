package seminar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RSlowServiceApplication {
	@GetMapping("/slow-api")
	public String slowApi(String idx) throws InterruptedException {
		Thread.sleep(1000);
		return "slow-api" + idx;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RSlowServiceApplication.class, args);
	}

}
