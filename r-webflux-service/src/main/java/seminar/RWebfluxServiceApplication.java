package seminar;

import java.time.Duration;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@Slf4j
public class RWebfluxServiceApplication {
	private WebClient client = WebClient.create();
	
	@GetMapping("/webflux-service")
	private Mono<String> webfluxService(int idx) {
		return client.get()
			.uri("http://localhost:8081/slow-api?idx={idx}", idx)
			.exchange()
			.flatMap(cr -> cr.bodyToMono(String.class))
			.log();
	}
	
//	@GetMapping("/webflux-service")
//	private Mono<String> webfluxService(int idx) {
//		Mono<String> mono = client.get()
//			.uri("http://localhost:8081/slow-api?idx={idx}", idx)
//			.exchange()
//			.flatMap(cr -> {
//				return cr.bodyToMono(String.class);
//			});
//		
//		mono.subscribe(new Subscriber<String>() {
//			private Subscription s;
//			@Override
//			public void onSubscribe(Subscription sub) {
//				log.info("# start subscription");
//				s = sub;
//				s.request(1);
//			}
//
//			@Override
//			public void onNext(String t) {
//				log.info("# onNext : {}", t);
//				s.request(1);
//			}
//
//			@Override
//			public void onError(Throwable t) {
//				log.error("# error occur!", t);
//			}
//
//			@Override
//			public void onComplete() {
//				log.info("# onComplete");
//			}
//		});
//		
//		return mono;
//	}
	
	@GetMapping("/webflux-service-timelimit")
	private Mono<String> webfluxServiceTimelimit(int idx) {
		return client.get()
			.uri("http://localhost:8081/slow-api?idx={idx}", idx)
			.exchange()
			.flatMap(cr -> cr.bodyToMono(String.class))
			.timeout(Duration.ofSeconds(1))
			.onErrorReturn("나중에 시도하세요")
			.log();
	}
	
	public static void main(String[] args) {
		/** 
		 * https://stackoverflow.com/questions/46925508/default-number-of-threads-in-spring-boot-2-0-reactive-webflux-configuration
		 */
		System.setProperty("reactor.netty.ioWorkerCount", "1");
		SpringApplication.run(RWebfluxServiceApplication.class, args);
	}

}
