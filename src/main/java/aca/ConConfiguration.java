package aca;

import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.zaxxer.hikari.HikariDataSource;

import aca.wsdl.client.TituloWebService;

@Configuration
public class ConConfiguration implements WebMvcConfigurer{
	
	@Bean(name="dsEnoc")
	DataSource dataSourceEnoc(){
		
		 HikariDataSource hikariDataSource = new HikariDataSource();
		//  hikariDataSource.setJdbcUrl("jdbc:oracle:thin:@74.208.80.211:1521:xe");
		 hikariDataSource.setJdbcUrl("jdbc:oracle:thin:@172.16.203.160:1521:xe");
		//  hikariDataSource.setJdbcUrl("jdbc:oracle:thin:@172.16.209.247:1521:xe");
		//  hikariDataSource.setJdbcUrl("jdbc:oracle:thin:@172.16.209.245:1521:xe");
		 hikariDataSource.setUsername("enoc");
		 hikariDataSource.setPassword("caminacondios");
		 hikariDataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		 hikariDataSource.setMaximumPoolSize(49);
		 hikariDataSource.setMinimumIdle(1);
		 hikariDataSource.setIdleTimeout(30000);
		 hikariDataSource.setLeakDetectionThreshold(99000);
		 hikariDataSource.setMaxLifetime(900000);		 
		 hikariDataSource.setAutoCommit(true);
		 hikariDataSource.setPoolName("poolEnoc");
		 
		 return hikariDataSource;		
	}
	
	@Bean(name = "jdbcEnoc")
	JdbcTemplate jdbcEnoc() {
		return new JdbcTemplate(dataSourceEnoc());
	}	

	@Bean(name = "dsArchivo")	
	DataSource dataSourceArchivo() {
		
		HikariDataSource hikariDataSource = new HikariDataSource();		 
		// hikariDataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/archivo");
		hikariDataSource.setJdbcUrl("jdbc:postgresql://172.16.203.160:5432/archivo");
		// hikariDataSource.setJdbcUrl("jdbc:postgresql://172.16.209.247:5432/archivo");
		// hikariDataSource.setJdbcUrl("jdbc:postgresql://172.16.209.245:5432/archivo");
		hikariDataSource.setUsername("postgres");
		hikariDataSource.setPassword("ptstann69");		
		hikariDataSource.setDriverClassName("org.postgresql.Driver");
		hikariDataSource.setMaximumPoolSize(10);
		hikariDataSource.setMinimumIdle(0);
		hikariDataSource.setIdleTimeout(30000);
		hikariDataSource.setLeakDetectionThreshold(99000);
		hikariDataSource.setMaxLifetime(1200000);
		hikariDataSource.setAutoCommit(true);
		hikariDataSource.setPoolName("poolArchivo");
		
		return hikariDataSource;
	}	
	
	@Bean(name = "jdbcArchivo")
	JdbcTemplate jdbcArchivo() {
		return new JdbcTemplate(dataSourceArchivo());
	}	
	
	@Bean(name = "dsAdmision")	
	DataSource dataSourceAdmision() {
		
		HikariDataSource hikariDataSource = new HikariDataSource();		 
		// hikariDataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/academico");
		hikariDataSource.setJdbcUrl("jdbc:postgresql://172.16.203.160:5432/academico");
		// hikariDataSource.setJdbcUrl("jdbc:postgresql://172.16.209.247:5432/academico");
		// hikariDataSource.setJdbcUrl("jdbc:postgresql://172.16.209.245:5432/academico");
		hikariDataSource.setUsername("postgres");
		hikariDataSource.setPassword("ptstann69");		
		hikariDataSource.setDriverClassName("org.postgresql.Driver");
		hikariDataSource.setMaximumPoolSize(10);
		hikariDataSource.setMinimumIdle(0);
		hikariDataSource.setIdleTimeout(30000);
		hikariDataSource.setLeakDetectionThreshold(77000);
		hikariDataSource.setMaxLifetime(1200000);
		hikariDataSource.setAutoCommit(true);
		hikariDataSource.setPoolName("poolAdmision");
		
		return hikariDataSource;
	}	
	
	@Bean(name = "jdbcAdmision")
	JdbcTemplate jdbcAdmision() {
		return new JdbcTemplate(dataSourceAdmision());
	}	
	
	@Bean(name = "dataSourceExa")	
	DataSource dataSourceExa() {
		HikariDataSource hikariDataSource = new HikariDataSource();		
		// hikariDataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/examen_um");
		hikariDataSource.setJdbcUrl("jdbc:postgresql://172.16.203.160:5432/examen_um");
		// hikariDataSource.setJdbcUrl("jdbc:postgresql://172.16.209.247:5432/examen_um");
		// hikariDataSource.setJdbcUrl("jdbc:postgresql://172.16.209.245:5432/examen_um");
		hikariDataSource.setUsername("postgres");
		hikariDataSource.setPassword("ptstann69");
		hikariDataSource.setDriverClassName("org.postgresql.Driver");
		hikariDataSource.setMaximumPoolSize(10);
		hikariDataSource.setMinimumIdle(0);
		hikariDataSource.setIdleTimeout(30000);
		hikariDataSource.setLeakDetectionThreshold(77000);
		hikariDataSource.setMaxLifetime(1200000);
		hikariDataSource.setAutoCommit(true);
		hikariDataSource.setPoolName("poolExamen");
		
		return hikariDataSource;
	}
	
	@Bean(name = "jdbcExa")
	JdbcTemplate jdbcExa() {
		return new JdbcTemplate(dataSourceExa());
	}
	
	@Bean
	Jaxb2Marshaller marshaller(){
		Jaxb2Marshaller marshaller = null;
		try {
			marshaller = new Jaxb2Marshaller();
			marshaller.setContextPath("aca.wsdl.sep");
		}catch(Exception e){
			System.out.println("Error context:"+e);
		}	
		return marshaller;
	}
	
	@Bean
	TituloWebService tituloClient(Jaxb2Marshaller marshaller) {		
		TituloWebService titulo = new TituloWebService();
		titulo.setDefaultUri("http://app-msys.uienl.edu.mx:8044/WebPrueba"); 
		titulo.setMarshaller(marshaller);
		titulo.setUnmarshaller(marshaller);
		return titulo;
	}
	
	@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.getDefault());
		return localeResolver;
	}
	
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setIgnoreInvalidLocale(true);
		localeInterceptor.setParamName("IdiomaUsuario");
		return localeInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
    
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		return bCryptPasswordEncoder;
	}
					
}