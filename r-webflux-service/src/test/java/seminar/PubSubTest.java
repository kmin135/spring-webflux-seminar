package seminar;

import java.util.Arrays;
import java.util.Iterator;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PubSubTest {
	public static void main(String[] args) throws InterruptedException {
		// Publisher <- Observable
		// Subscriber <- Observer
		Iterable<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);

		Publisher<Integer> pub = new Publisher<Integer>() {
			Iterator<Integer> it = itr.iterator();

			@Override
			public void subscribe(Subscriber<? super Integer> subscriber) {
				subscriber.onSubscribe(new Subscription() {
					@Override
					public void request(long n) {
						log.info("request {}", n);
						int i = 0;
						while (i++ < n) {
							if (it.hasNext()) {
								subscriber.onNext(it.next());
							} else {
								subscriber.onComplete();
								break;
							}
						}
					}

					@Override
					public void cancel() {
						log.info("cancel");
					}
				});
			}
		};

		Subscriber<Integer> sub = new Subscriber<Integer>() {
			Subscription subscription;

			@Override
			public void onSubscribe(Subscription s) {
				log.info("onSubscribe");
				this.subscription = s;
				s.request(1);
			}

			@Override
			public void onNext(Integer t) {
				log.info("onNext {}", t);
				// 여기서 backpressure 조정
				this.subscription.request(1);
			}

			@Override
			public void onError(Throwable t) {
				log.error("onError {}", t.toString());
			}

			@Override
			public void onComplete() {
				log.info("onComplete");
			}
		};

		pub.subscribe(sub);
	}
}