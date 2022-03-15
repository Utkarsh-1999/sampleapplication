package com.example.sampleapplication.ratelimiter.mechanism.tokenbucket;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
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
//        Config config = Config.fromYAML(new File("src/main/resources/redisson.yaml"));
//        Config config=new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//        RedissonClient redisson = Redisson.create(config);

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


}
