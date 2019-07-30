package seminar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RedisController {
	@Autowired
	private RedisService service;
	
	@PostMapping("/{key}")
    public boolean put(@PathVariable("key") String key,
                             @RequestBody User user) {
		service.put(key, user);
        return true;
    }

    @GetMapping("/{key}")
    public User get(@PathVariable("key") String key) {
        return service.get(key);
    }
}
