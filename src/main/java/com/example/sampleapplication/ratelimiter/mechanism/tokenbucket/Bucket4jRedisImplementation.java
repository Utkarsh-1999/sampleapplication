package com.example.sampleapplication.ratelimiter.mechanism.tokenbucket;

import com.example.sampleapplication.exception.ratelimiter.RateLimitExceededException;
import com.example.sampleapplication.ratelimiter.keygenerator.RequestKey;
import com.example.sampleapplication.ratelimiter.mechanism.RateLimiter;
import io.github.bucket4j.*;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;
import io.github.bucket4j.distributed.proxy.RemoteBucketBuilder;
import io.github.bucket4j.redis.redisson.cas.RedissonBasedProxyManager;
import org.redisson.command.CommandExecutor;
import org.redisson.command.CommandSyncService;
import org.redisson.config.Config;
import org.redisson.connection.ConnectionManager;
import org.redisson.connection.SingleConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Component
public class Bucket4jRedisImplementation implements RateLimiter {

    RedissonBasedProxyManager redissonBasedProxyManager;

    BucketConfiguration bucketConfiguration;
    RemoteBucketBuilder<String> remoteBucketBuilder;

    @Autowired
    Bucket4jRedisImplementation(ClientSideConfig clientSideConfig,Config config,UUID uuid,List<Bandwidth> bandwidths,Duration ttl){

        ConnectionManager connectionManager=new SingleConnectionManager(config.useSingleServer(), config,uuid);
        CommandExecutor commandExecutor=new CommandSyncService(connectionManager);

        this.redissonBasedProxyManager=new RedissonBasedProxyManager(commandExecutor,clientSideConfig, ttl);

        bucketConfiguration=new BucketConfiguration(bandwidths);
        remoteBucketBuilder=redissonBasedProxyManager.builder();
    }

    @Override
    public void tryAcceptRequest(RequestKey requestKey) {

        BucketProxy bucketProxy=remoteBucketBuilder.build(requestKey.getKey(),bucketConfiguration);
        if(!bucketProxy.tryConsume(1))
        {
            throw new RateLimitExceededException();
        }

    }

    @Override
    public void tryIncrementAllowedRequest(RequestKey requestKey) {

        BucketProxy bucketProxy=remoteBucketBuilder.build(requestKey.getKey(),bucketConfiguration);
        bucketProxy.addTokens(1);

    }
}
