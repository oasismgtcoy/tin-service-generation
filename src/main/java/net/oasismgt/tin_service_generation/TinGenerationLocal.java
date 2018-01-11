package net.oasismgt.tin_service_generation;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class TinGenerationLocal {
	private final static Logger logger = LoggerFactory.getLogger(TinGenerationLocal.class);
	

	@PostConstruct
	  void started() {
	 //   TimeZone.setDefault(TimeZone.getTimeZone("UTC+01:00"));
	    TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
	  }
	
	public static void main(String... param){
		SpringApplication.run(TinGenerationLocal.class, param);		
	}
	
	
	
}
