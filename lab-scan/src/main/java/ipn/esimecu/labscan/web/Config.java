package ipn.esimecu.labscan.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class Config {
    public static final String TASK_EXECUTOR_DEFAULT_BEAN_NAME = "thread_pool_task_executor_default";

    @Bean(name = TASK_EXECUTOR_DEFAULT_BEAN_NAME)
    public ThreadPoolTaskExecutor taskExecutorDefault() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async-1-");
        executor.initialize();
        return executor;
    }

    


}
