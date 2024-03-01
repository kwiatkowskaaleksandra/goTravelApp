package biuropodrozy.gotravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Main application class for GoTravel.
 * This class initializes and runs the GoTravel application. It also configures asynchronous execution using
 * Spring's `@EnableAsync` annotation and customizes the task executor for async methods.
 */
@SpringBootApplication
@EnableAsync
public class GoTravelApplication {

    /**
     * The entry point of the application.
     *
     * @param args The input arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(GoTravelApplication.class, args);
    }

    /**
     * Configures the task executor for asynchronous methods.
     *
     * @return The configured task executor.
     */
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.initialize();
        return executor;
    }
}
