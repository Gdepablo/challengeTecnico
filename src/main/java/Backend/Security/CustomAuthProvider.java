package Backend.Security;

import Backend.Configuration.BCryptHelper;
import Backend.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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

            String hashedPassword = userDetails.getPassword();

            if (!passwordEncoder.matches(password,hashedPassword)) {
                throw new BadCredentialsException("Wrong password.");
            }


            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
        }
    }
