package Backend.Configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class BCryptHelper {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();}
}
