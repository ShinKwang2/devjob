package com.shinkwang.devjob.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        GenericJacksonJsonRedisSerializer valueSerializer =
                GenericJacksonJsonRedisSerializer.builder()
                        .enableDefaultTyping(redisTypeValidator())
                        .customize(mapperBuilder -> mapperBuilder.disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS))
                        .build();

        return RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(cacheName -> "devjob:cache:" + cacheName + "::")
                .entryTtl(Duration.ofSeconds(60))
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8)
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer)
                );
    }

    private PolymorphicTypeValidator redisTypeValidator() {
        return BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.shinkwang.devjob.")
                .allowIfSubType("java.util.")
                .build();
    }
}
