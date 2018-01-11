package net.oasismgt.tin_service_generation.local.configs;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.SpringJtaSessionContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.hibernate5.SpringSessionContext;


@Configuration
@EnableTransactionManagement
public class AppHibernateConfig {
	
	 @Autowired  private Environment env;
	 @Autowired  private DataSource dataSource;
	 @Autowired  private SessionFactory sessionFactory;
	 
	  @Bean
	  public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
	    dataSource.setUrl(env.getProperty("spring.datasource.url"));
	    dataSource.setUsername(env.getProperty("spring.datasource.username"));
	    dataSource.setPassword(env.getProperty("spring.datasource.password"));
	    return dataSource;
	  }
	  
	   @Bean
	    public LocalSessionFactoryBean sessionFactory() {
		  LocalSessionFactoryBean localSessionFactory=new LocalSessionFactoryBean();
		  localSessionFactory.setDataSource(dataSource);
		  Properties additionalProperties = new Properties();
		  additionalProperties.put("hibernate.dialect",env.getProperty("spring.jpa.database-platform"));
		  additionalProperties.put("hibernate.show_sql",env.getProperty("spring.jpa.show-sql"));
		  additionalProperties.put("hibernate.hbm2ddl.auto",env.getProperty("spring.jpa.hibernate.ddl-auto")); 
		  
		//  additionalProperties.put("hibernate.implicit_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
		//  additionalProperties.put("hibernate.physical_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
		 
	  //  additionalProperties.put("hibernate.current_session_context_class","managed");
		  localSessionFactory.setHibernateProperties(additionalProperties);
		  localSessionFactory.setPackagesToScan("net.oasismgt.tin_service_generation.local.model");		  
		return localSessionFactory;		  
	  }
	  
	  @Bean
	  public HibernateTransactionManager transactionManager() {
		  HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		  transactionManager.setSessionFactory(sessionFactory);
	    return transactionManager;
	  }
	    
	  @Bean
	  public LocalContainerEntityManagerFactoryBean entityManager() {		
	    LocalContainerEntityManagerFactoryBean entityManagerFactory =new LocalContainerEntityManagerFactoryBean();	    
	    entityManagerFactory.setDataSource(dataSource);
	    entityManagerFactory.setPackagesToScan("net.oasismgt.tin_service_generation.local.model");	    
	    entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	    return entityManagerFactory;
	  }

/*
	//@Bean
	  public JpaTransactionManager transactionManager2() {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(sessionFactory);
	    return transactionManager;
	  }
	 // @Bean
		public PlatformTransactionManager txManager(){
			return new JpaTransactionManager(sessionFactory);
		}

	//  @Bean
		public HibernateTransactionManager hibernateTransactionManager(){
		  HibernateTransactionManager txMgr= new HibernateTransactionManager();
		  txMgr.setAutodetectDataSource(true);
		  txMgr.setSessionFactory(sessionFactory);		  
		     return txMgr;
		}
	  
	//  @Bean
	  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	    return new PersistenceExceptionTranslationPostProcessor();
	  }
		
	  
	//  @Bean
	//  @Qualifier("jpaSessionFactory")
	  public HibernateJpaSessionFactoryBean hibernateJpaSessionFactory() {
	      return new HibernateJpaSessionFactoryBean();
	  }*/
	 	
}
