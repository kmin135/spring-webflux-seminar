package seminar;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadTest {
	static AtomicInteger counter = new AtomicInteger(0);
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		int threadSize = 100;
		ExecutorService es = Executors.newFixedThreadPool(threadSize);
		
		RestTemplate rt = new RestTemplate();
		//1. 전통적인 api
//		String url = "http://localhost:8080/service?idx={idx}";
		
		//2. spring4 를 이용해 개선된 api
//		String url = "http://localhost:8082/spring4-service?idx={idx}";

		//3. webflux 를 이용해 개선된 api
		String url = "http://localhost:8083/webflux-service?idx={idx}";
		
		CyclicBarrier barrier = new CyclicBarrier(threadSize + 1);
		
		for(int i=0;i<threadSize;i++) {
			es.submit(() -> {
				int idx = counter.addAndGet(1);
				
				barrier.await();
				
				StopWatch sw = new StopWatch();
				sw.start();
				
				String result = rt.getForObject(url, String.class, idx);
				
				sw.stop();
				log.info("Elapsed: {}, body : {}", sw.getTotalTimeSeconds(), result);
				return null;
			});
		}
		
		barrier.await();
		StopWatch main = new StopWatch();
		main.start();
		
		es.shutdown();
		es.awaitTermination(100, TimeUnit.SECONDS);
		
		main.stop();
		log.info("Total Elapsed: {}", main.getTotalTimeSeconds());
	}
}
