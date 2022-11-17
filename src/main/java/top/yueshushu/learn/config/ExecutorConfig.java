package top.yueshushu.learn.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import top.yueshushu.learn.common.Const;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * @author yuejianli
 * 线程池配置
 */
@Slf4j
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Value("${async.executor.thread.core_pool_size:200}")
    private int corePoolSize;
    @Value("${async.executor.thread.max_pool_size:200}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queue_capacity:100}")
    private int queueCapacity;
    @Value("${async.executor.thread.name.prefix:stock-async-service-}")
    private String namePrefix;

    /**
     * 其他场景的异步线程池
     */
    @Bean(name = Const.ASYNC_SERVICE_EXECUTOR_BEAN_NAME)
    public Executor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        return buildExecutor(corePoolSize, maxPoolSize, queueCapacity, namePrefix, Thread.NORM_PRIORITY);
    }

    private Executor buildExecutor(int corePoolSize, int maxPoolSize, int queueCapacity, String namePrefix, int threadPriority) {

        log.info("namePrefix:{}, corePoolSize:{}, maxPoolSize:{}, queueCapacity:{}",
                namePrefix, corePoolSize, maxPoolSize, queueCapacity);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(namePrefix);
        executor.setThreadPriority(threadPriority);

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
