package com.example.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {
  
  @Autowired
  StringRedisTemplate redisTemplate;
  
  @PostMapping(value = "/redis-study/keys", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void putKeys(@RequestParam String key, @RequestParam String value) {
    ValueOperations<String, String> operation = redisTemplate.opsForValue();
    operation.set(key, value);
    redisTemplate.expire(key, 20, TimeUnit.SECONDS);
  }

  @GetMapping(value = "/redis-study/keys", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @Cacheable(cacheNames = "keys")
  public Map<String, String> getValue(@RequestParam String key) {
    ValueOperations<String, String> operation = redisTemplate.opsForValue();
    String value = operation.get(key);
    HashMap<String, String> map = new HashMap<>();
    map.put("value", value);
    return map;
  }
}
