package Backend.services;

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
        private final BCryptPasswordEncoder passwordEncoder;

        @Autowired
        public CustomAuthProvider(UserServiceImpl userServiceImpl, BCryptPasswordEncoder passwordEncoder) {
            this.userServiceImpl = userServiceImpl;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String username = authentication.getName();
            String password = (String) authentication.getCredentials();

            UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
            if (userDetails == null || !userDetails.getUsername().equalsIgnoreCase(username)) {
                throw new BadCredentialsException("Username not found.");
            }

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Wrong password.");
            }

            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
        }
    }
