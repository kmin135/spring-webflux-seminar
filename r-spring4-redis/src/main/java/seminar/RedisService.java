package seminar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	public void put(String key, User user) {
		redisTemplate.opsForValue().set(key, user);
	}
	
	public User get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
}
