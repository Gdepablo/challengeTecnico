package Backend.controller;

import Backend.Security.CustomAuthProvider;
import Backend.Security.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

@CrossOrigin(origins = "localhost:4200")
@Controller //Se le deja controller para que renderice el html y no restController
public class LoginController{


  private final CustomAuthProvider providerManager;

  @Autowired
  public LoginController(CustomAuthProvider providerManager) {
    this.providerManager = providerManager;
  }

  @GetMapping("/login")
  public String login() {
    return "login.html";
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginForm loginForm) {
    try {
      Authentication auth = providerManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(auth);
      return String.valueOf(HttpStatus.OK);}
    catch (AuthenticationException e) {
      return "login";
    }}

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