package bleeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;

@Configuration
@ComponentScan(basePackages = {"bleeter.bleets", "bleeter.users"})
@EnableAutoConfiguration
@Import({MongoFactoryConfig.class, SecurityConfiguration.class, MvcConfig.class})
public class BleeterApplication extends SpringBootServletInitializer implements WebApplicationInitializer{

    public static void main(String[] args) {
        SpringApplication.run(BleeterApplication.class, args);
    }
}
