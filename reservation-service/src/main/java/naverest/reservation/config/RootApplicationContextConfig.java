package naverest.reservation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
     "naverest.reservation.dao",
     "naverest.reservation.service"
})
@Import({DbConfig.class})
public class RootApplicationContextConfig {
}
