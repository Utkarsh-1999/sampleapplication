package com.example.sampleapplication.ratelimiter.mechanism.tokenbucket;

import com.example.sampleapplication.ratelimiter.keygenerator.RequestKey;
import com.example.sampleapplication.ratelimiter.mechanism.RateLimiter;
import io.github.bucket4j.*;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.AsyncBucketProxy;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.proxy.AsyncProxyManager;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;
import io.github.bucket4j.distributed.proxy.DefaultBucketProxy;
import io.github.bucket4j.distributed.proxy.RemoteBucketBuilder;
import io.github.bucket4j.distributed.remote.RemoteBucketState;
import io.github.bucket4j.distributed.remote.RemoteCommand;
import io.github.bucket4j.distributed.remote.Request;
import io.github.bucket4j.distributed.remote.commands.AddTokensCommand;
import io.github.bucket4j.distributed.remote.commands.CreateInitialStateCommand;
import io.github.bucket4j.distributed.remote.commands.TryConsumeCommand;
import io.github.bucket4j.distributed.remote.commands.VerboseCommand;
import io.github.bucket4j.redis.redisson.cas.RedissonBasedProxyManager;
import org.aspectj.weaver.ast.Not;
import org.redisson.Redisson;
import org.redisson.RedissonAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.RedisCommand;
import org.redisson.command.CommandExecutor;
import org.redisson.command.CommandSyncService;
import org.redisson.config.Config;
import org.redisson.connection.ConnectionManager;
import org.redisson.connection.SingleConnectionManager;
import org.redisson.liveobject.core.RedissonObjectBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class Bucket4jRedisImplementation implements RateLimiter {

    RedissonBasedProxyManager redissonBasedProxyManager;
    ClientSideConfig clientSideConfig;
    Config config;
    BucketConfiguration bucketConfiguration;
    RemoteBucketBuilder<String> remoteBucketBuilder;



    @Autowired
    Bucket4jRedisImplementation(ClientSideConfig clientSideConfig,Config config){

        this.clientSideConfig=clientSideConfig;
        this.config=config;
        UUID uuid=new UUID(8,2);
        RedissonClient redissonClient=Redisson.create(config);
        RedissonObjectBuilder redissonObjectBuilder=new RedissonObjectBuilder(redissonClient);

        ConnectionManager connectionManager=new SingleConnectionManager(config.useSingleServer(), config,uuid);
        CommandExecutor commandExecutor=new CommandSyncService(connectionManager,redissonObjectBuilder);

        this.redissonBasedProxyManager=new RedissonBasedProxyManager(commandExecutor,clientSideConfig, Duration.ofMinutes(1));
        List<Bandwidth> bandwidths=new ArrayList<>();
        bandwidths.add(Bandwidth.classic(10,Refill.intervally(1,Duration.ofMinutes(1))));
        bucketConfiguration=new BucketConfiguration(bandwidths);
        remoteBucketBuilder=redissonBasedProxyManager.builder();
    }

    @Override
    public void tryAcceptRequest(RequestKey requestKey) {



        BucketProxy bucketProxy=remoteBucketBuilder.build(requestKey.getKey(),bucketConfiguration);



        bucketProxy.tryConsume(1);


    }

    @Override
    public void tryIncrementAllowedRequest(RequestKey requestKey) {


        BucketProxy bucketProxy=remoteBucketBuilder.build(requestKey.getKey(),bucketConfiguration);

        bucketProxy.addTokens(1);

    }
}
