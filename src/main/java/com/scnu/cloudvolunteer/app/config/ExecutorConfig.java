package com.scnu.cloudvolunteer.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zzheng
 * @date 2020/05/27
 * @description：
 *  线程池、调度器配置
 */
@Configuration
@EnableAsync
public class ExecutorConfig {
    /**
     * 核心线程数量
     */
    private static final int CORE_POOL_SIZE = 50;
    /**
     * 最大线程数量
     */
    private static final int MAX_POOL_SIZE = 300;
    /**
     * 线程存活时间
     */
    private static final int KEEP_ALIVE_TIME = 10;
    /**
     * 等待队列长度
     */
    private static final int QUEUE_CAPACITY= 200;
    private static final String THREAD_NAME_PREFIX = "cloudVolunteer-Executor-";
//    private static final String SCHEDULER_NAME_PREFIX = "cloudVolunteer-Scheduker-";

    @Bean("cloudExecutor")
    public ThreadPoolTaskExecutor roadExecutor() {
        ThreadPoolTaskExecutor roadExecutor = new ThreadPoolTaskExecutor();
        roadExecutor.setCorePoolSize(CORE_POOL_SIZE);
        roadExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        roadExecutor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        roadExecutor.setQueueCapacity(QUEUE_CAPACITY);
        roadExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        roadExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        roadExecutor.initialize();
        return roadExecutor;
    }

//    @Bean("cloudScheduler")
//    public ThreadPoolTaskScheduler roadScheduler() {
//        ThreadPoolTaskScheduler roadScheduler = new ThreadPoolTaskScheduler();
//        roadScheduler.setPoolSize(MAX_POOL_SIZE);
//        roadScheduler.setThreadNamePrefix(SCHEDULER_NAME_PREFIX);
//        roadScheduler.initialize();
//        return roadScheduler;
//    }
}

