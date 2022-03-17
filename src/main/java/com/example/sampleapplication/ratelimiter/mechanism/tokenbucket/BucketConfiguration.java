package com.example.sampleapplication.ratelimiter.mechanism.tokenbucket;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;

import io.github.bucket4j.distributed.versioning.Version;
import io.github.bucket4j.redis.redisson.cas.RedissonBasedProxyManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.command.CommandExecutor;
import org.redisson.command.CommandSyncService;
import org.redisson.config.Config;
import org.redisson.config.RedissonNodeConfig;
import org.redisson.connection.ConnectionManager;
import org.redisson.connection.SingleConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Configuration
public class BucketConfiguration {


    @Bean
    public ClientSideConfig getClientSideConfig() {

        return ClientSideConfig.getDefault();
    }

    @Bean
    public Config getConfig() throws IOException {

        return Config.fromYAML(new File("src/main/resources/redisson.yaml"));


    }

    @Bean
    public UUID getUUID(){
        return new UUID(64,2);
    }


    @Bean
    public List<Bandwidth> getBandwiths(){
        List<Bandwidth> bandwidths=new ArrayList<>();
        bandwidths.add(Bandwidth.classic(10, Refill.intervally(1, Duration.ofMinutes(1))));
        return bandwidths;
    }

    @Bean
    public Duration getRedisEntryTll(){
        return Duration.ofMinutes(5);
    }



}
