package task;

/**
 * Created by mujiang on 2017/10/26.
 */

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("main.java.task")
@EnableScheduling
public class ScheduleConfig {
}
