package Backend.Configuration;

//import Backend.services.CustomAuthProvider;
import Backend.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginConfiguration {


    private final CustomAuthProvider customAuthProvider;


    @Autowired
    public LoginConfiguration(CustomAuthProvider customAuthProvider) {
       this.customAuthProvider = customAuthProvider;    }
    //Parece que no se usan pero el login funciona, entonces no se.


    public void configure(HttpSecurity http) throws Exception {
        //Se encarga del form de login y logout y de que antes de poder hacer cualquier cosa estes logeado.
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll().and().authenticationProvider(customAuthProvider);

        //Esto se ocupa de la autenticacion de la app tambien. Basicamente lo que hace es que cualquier request es atrapado
        // por este metodo y hacer que este autenticado. Form login te crea como un template basico para el login via web
        // y permitAll permite que todos puedan acceder a la pagina del login, al igual que la del logout(por el and)
    }

    @Bean
    public ProviderManager providerManager(CustomAuthProvider customAuthProvider) {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(customAuthProvider);
        return new ProviderManager(providers);
    } //Necesario para poder autowire-isarlo. O como se escriba.

}*/
