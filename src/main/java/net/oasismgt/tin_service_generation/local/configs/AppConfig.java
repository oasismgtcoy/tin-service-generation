package net.oasismgt.tin_service_generation.local.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AppConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	@Bean
	public TaskExecutor  taskExecutor(){
		return new ThreadPoolTaskExecutor();
	}
}
