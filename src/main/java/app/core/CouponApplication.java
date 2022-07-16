package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import app.core.system.LoginFilter;
import app.core.system.SessionManagaer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@CrossOrigin
public class CouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponApplication.class, args);

	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("app"))
				.paths(PathSelectors.ant("/**")).build();
	}

	@Bean
	public FilterRegistrationBean<LoginFilter> filterRegistration(SessionManagaer sessionManagaer) {
		FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<LoginFilter>();
		LoginFilter filter  = new LoginFilter(sessionManagaer);
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.addUrlPatterns("/admin/**" , "/company/**","/customer/**");
		return filterRegistrationBean;
	}
	
}
