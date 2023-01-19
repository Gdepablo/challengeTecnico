package Backend.services;

import Backend.Configuration.BCryptHelper;
import Backend.component.User;
import Backend.component.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthProvider implements AuthenticationProvider {

        private final UserServiceImpl userServiceImpl;
        private final BCryptPasswordEncoder passwordEncoder = BCryptHelper.passwordEncoder();

        @Autowired
        public CustomAuthProvider(UserServiceImpl userServiceImpl) {
            this.userServiceImpl = userServiceImpl;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();



            UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
            UserDTO user = userServiceImpl.getUserByUsername(username);
            //No valido el username porque ya esta validado en otro lado.

            String hashedPassword = userDetails.getPassword();
            String salt = user.getSalt();
            System.out.println(BCrypt.hashpw(password,user.getSalt()));
            System.out.println(BCrypt.hashpw(password,user.getSalt()));
            System.out.println("---------");
            System.out.println(hashedPassword);
            System.out.println("$2a$10$kfo7rdkxaJctVfML/pkpH.8be/C5zNBwmwvqpSgr1dBXNY43ONL5a");

            if (!BCrypt.checkpw(BCrypt.hashpw(password,user.getSalt()),hashedPassword)) {
                throw new BadCredentialsException("Wrong password.");
            }


            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
        }
    }
