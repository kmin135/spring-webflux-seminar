package seminar;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class FluxController {
	/**
	 * HTML5 표준 기술인 Server-Sent Events (SSE) 예제
	 * Content-Type: text/event-stream
	 * @return
	 */
	@GetMapping(value="/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Long> flux() {
		return Flux.interval(Duration.ofSeconds(1))
				.take(10)
				.log();
	}
}
