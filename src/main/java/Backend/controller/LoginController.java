package Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
public class LoginController{


    private final ProviderManager providerManager;

    @Autowired
    public LoginController(ProviderManager providerManager) {
        this.providerManager = providerManager;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") @NotBlank String username,
                        @RequestParam("password") @NotBlank String password, Model model) {
        if(username == null || password == null) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
        try {
            Authentication auth = providerManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/notes";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

}