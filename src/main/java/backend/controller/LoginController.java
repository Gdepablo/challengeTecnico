package Backend.controller;

import Backend.services.CustomAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

@RestController
public class LoginController{


    private final CustomAuthProvider providerManager;

    @Autowired
    public LoginController(CustomAuthProvider providerManager) {
        this.providerManager = providerManager;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") @NotBlank String username,
                        @RequestParam("password") @NotBlank String password) {
        try {
            Authentication auth = providerManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/notes"; }
        catch (AuthenticationException e) {
            return "login";
        }}
        //Sigo borrando cosas q son innecesarias.

        @PostMapping("/logout")
        public String logout(HttpServletRequest request) {
            HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate();
            }
            SecurityContextHolder.clearContext();
            return "redirect:/login";
        }


}