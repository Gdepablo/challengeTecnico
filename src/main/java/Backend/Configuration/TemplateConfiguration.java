package Backend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

    /*@Autowired
    CustomAuthProvider authProvider;

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated().
                and().httpBasic().and().
                csrf().disable().
                formLogin().disable();
        return http.build();
    }

    //NO ES NECESARIO EL VIEW RESOLVER PORQUE YA LO CONFIGURÉ EN LA APP.PROPERTIES LO DEL PREFIX Y SUFFIX.
}

