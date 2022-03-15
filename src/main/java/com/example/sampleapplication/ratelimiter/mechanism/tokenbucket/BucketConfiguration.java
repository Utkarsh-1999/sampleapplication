package com.example.sampleapplication.ratelimiter.mechanism.tokenbucket;

import io.github.bucket4j.distributed.proxy.ClientSideConfig;

import io.github.bucket4j.distributed.versioning.Version;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.RedissonNodeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;


@Configuration
public class BucketConfiguration {


    @Bean
    public ClientSideConfig getClientSideConfig() {

        return ClientSideConfig.getDefault();
    }

    @Bean
    public Config getConfig() throws IOException {
//        Config config = Config.fromYAML(new File("src/main/resources/redisson.yaml"));
        Config config=new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);
        return redisson.getConfig();
    }
}
