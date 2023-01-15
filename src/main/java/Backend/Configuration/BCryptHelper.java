package Backend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration //Se ve que al usar Configuration y Bean no es necesario el autowired. El config es pa q lo detecte automaticamente spring
//por classpath scanning creo y es tipo como que sabe que aca hay una configuracion pa que se divierta. idk.
public class BCryptHelper {
    @Bean
public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();}
}

//Bean es como para definir un componente, mientras que autowired es para inyectar componentes.

//@Bean is used in a configuration class, typically annotated with @Configuration.
// It is used to define beans for the Spring ApplicationContext.
// This annotation is typically used in conjunction with the @Configuration annotation to define beans.
//
//@Component is used to indicate that a class is a Spring Bean, without specifying how the bean will be created.
// It is more general-purpose and can be used in any class, not just in a configuration class.
// This annotation is typically used on classes that will be automatically detected by classpath scanning.
//
//In terms of functionality, they are equivalent, but it's considered best practice to use @Bean
// in configuration classes, and @Component in other classes.
