package seminar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RWebfluxRedisApplication {

	public static void main(String[] args) {
//    	System.setProperty("reactor.netty.ioWorkerCount", "1");
		SpringApplication.run(RWebfluxRedisApplication.class, args);
	}

}
