package seminar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import io.netty.channel.nio.NioEventLoopGroup;

@SpringBootApplication
@RestController
public class RSpring4ServiceApplication {
//	private AsyncRestTemplate rt = new AsyncRestTemplate();
	private AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
	
	@GetMapping("/spring4-service")
	public DeferredResult<String> spring4Service(String idx) {
		DeferredResult<String> dr = new DeferredResult<>();
		
		ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity("http://localhost:8081/slow-api?idx={idx}", String.class, idx);
		f1.addCallback(s -> {
			dr.setResult("spring4-" + idx + " / " + s.getBody());
		}, e -> {
			dr.setErrorResult(e.getMessage());
		});
		
		return dr;
	}
	
	@GetMapping("/callbackHell-service")
	public DeferredResult<String> callbackHell(String idx) {
		DeferredResult<String> dr = new DeferredResult<>();
		
		ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity("http://localhost:8081/slow-api?idx={idx}", String.class, idx);
		f1.addCallback(s -> {
			String idx2 = idx + "-" + s;
			
			ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity("http://localhost:8081/slow-api?idx={idx}", String.class, idx2);
			f2.addCallback(s2 -> {
				dr.setResult("callbackHell-" + idx2 + " / " + s.getBody());
			}, e2 -> {
				dr.setErrorResult(e2.getMessage());
			});
		}, e -> {
			dr.setErrorResult(e.getMessage());
		});
		
		return dr;
	}

	public static void main(String[] args) {
		SpringApplication.run(RSpring4ServiceApplication.class, args);
	}

}
