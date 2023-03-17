package top.yuan67.webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
  
  /**
   * @Description redis模板配置
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    template.setKeySerializer(stringRedisSerializer);
    template.setValueSerializer(stringRedisSerializer);
    template.setHashKeySerializer(stringRedisSerializer);
    template.setHashValueSerializer(stringRedisSerializer);
    template.setDefaultSerializer(stringRedisSerializer);
    template.afterPropertiesSet();
    return template;
  }
}