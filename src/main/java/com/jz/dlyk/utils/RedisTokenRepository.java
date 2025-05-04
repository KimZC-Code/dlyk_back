package com.jz.dlyk.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenRepository implements PersistentTokenRepository {
    private static final String REMEMBER_ME_KEY_PREFIX = "spring:security:rememberMe:";
    @Resource
    private RedisTemplate<String ,Object> redisTemplate;
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String key = getKey(token.getSeries());
        redisTemplate.opsForValue().set(
                key,
                token,
                token.getDate().getTime() + TimeUnit.DAYS.toMillis(30) - System.currentTimeMillis(),
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String key = getKey(series);
        PersistentRememberMeToken token = (PersistentRememberMeToken) redisTemplate.opsForValue().get(key);
        if (token != null) {
            PersistentRememberMeToken newToken = new PersistentRememberMeToken(
                    token.getUsername(),
                    series,
                    tokenValue,
                    lastUsed
            );
            redisTemplate.opsForValue().set(
                    key,
                    newToken,
                    newToken.getDate().getTime() + TimeUnit.DAYS.toMillis(30) - System.currentTimeMillis(),
                    TimeUnit.MILLISECONDS
            );
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return (PersistentRememberMeToken) redisTemplate.opsForValue().get(getKey(seriesId));
    }

    @Override
    public void removeUserTokens(String username) {
        // 注意：这种方法在大量token时效率不高
        // 生产环境可能需要更好的实现方式
        String pattern = REMEMBER_ME_KEY_PREFIX + "*";
        redisTemplate.keys(pattern).forEach(key -> {
            PersistentRememberMeToken token = (PersistentRememberMeToken) redisTemplate.opsForValue().get(key);
            if (token != null && username.equals(token.getUsername())) {
                redisTemplate.delete(key);
            }
        });
    }
    private String getKey(String series) {
        return REMEMBER_ME_KEY_PREFIX + series;
    }
}
