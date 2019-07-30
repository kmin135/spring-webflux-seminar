package seminar.controller;

import io.lettuce.core.protocol.CommandHandler;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import seminar.model.User;
import seminar.service.UserRedisService;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserRedisService userRedisService;
	 
    @PostMapping("/{key}")
    public Mono<Boolean> put(@PathVariable("key") String key,
                             @RequestBody Mono<User> userMono) {
    	return userMono
//    			.doOnNext(u -> log.info("#User ready : {}", u.getFullName()))
    			.flatMap(user -> userRedisService.put(key, user));
//    			.doOnNext(bool -> log.info("#User put result : {}", bool));
    }

    @GetMapping("/{key}")
    public Mono<User> get(@PathVariable("key") String key) {

        return userRedisService.get(key);
    }
}
