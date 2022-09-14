package com.fintest.testifi.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.fintest.testifi")
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertySourceFactory.class)
public class FinBankConfig implements WebMvcConfigurer {

	private static final String[] ENTITY_PACKAGES_TO_SCAN = new String[] { "com.fintest.testifi" };
	
	@Autowired
	private Environment env;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private FinBankMailConfigProperties mailConfigProps;
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.addPathPrefix(env.getProperty("api-base-path"), HandlerTypePredicate.forAnnotation(RestController.class));
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*");
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactory.setPackagesToScan(ENTITY_PACKAGES_TO_SCAN);
		entityManagerFactory.setJpaProperties(jpaVendorProperties());
		return entityManagerFactory;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	private Properties jpaVendorProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
		properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.setProperty("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
		return properties;		
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return mapper;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailConfigProps.getHost());
		mailSender.setPort(mailConfigProps.getPort());
		
	    mailSender.setUsername(mailConfigProps.getUsername());
	    mailSender.setPassword(mailConfigProps.getPassword());
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", mailConfigProps.getProtocol());
	    props.put("mail.smtp.auth", mailConfigProps.getAuth());
	    props.put("mail.smtp.starttls.enable", mailConfigProps.getTls());
	    props.put("mail.debug", mailConfigProps.getDebug());
	    
	    return mailSender;
	}
}
