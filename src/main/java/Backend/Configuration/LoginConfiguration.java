package Backend.Configuration;

import Backend.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class LoginConfiguration {

    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder = BCryptHelper.passwordEncoder();

    @Autowired
    public LoginConfiguration(UserServiceImpl userServiceImpl ) {
        this.userServiceImpl = userServiceImpl;
    }
    //Parece que no se usan pero el login funciona, entonces no se.
    public void compareCredentials(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder);
        //Se encarga de comparar las credenciales recibidas con las que estan en la DB.
    }

    public void setupLogin(HttpSecurity http) throws Exception {
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
                .permitAll();
    }
    //Esto se ocupa de la autenticacion de la app tambien. Basicamente lo que hace es que cualquier request es atrapado
    // por este metodo y hacer que este autenticado. Form login te crea como un template basico para el login via web
    // y permitAll permite que todos puedan acceder a la pagina del login, al igual que la del logout(por el and)
}
