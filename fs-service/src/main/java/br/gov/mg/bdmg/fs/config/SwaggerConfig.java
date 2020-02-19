package br.gov.mg.bdmg.fs.config;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

	private final ServletContext servletContext;

	@Autowired
	public SwaggerConfig(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	
	 @Override
	  public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addRedirectViewController("/fs/doc/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
	    registry.addRedirectViewController("/fs/doc/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
	    registry.addRedirectViewController("/fs/doc/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
	    registry.addRedirectViewController("/fs/doc/swagger-resources", "/swagger-resources");
	  }

	  @Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/fs/doc/**").addResourceLocations("classpath:/META-INF/resources/");
	  }	

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
//				.host("localhost:8080")
/*				
				.pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return "/fsXX";
                    }
                })
                				*/
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.gov.mg.bdmg.fs.controller")).paths(PathSelectors.any())
				.build().apiInfo(createInfo());
	}

	private ApiInfo createInfo() {
		return new ApiInfoBuilder().version("1.0").title("FileServer API").description("FileServer API v1.0").build();
	}

}
