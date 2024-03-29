package Backend.controller;

import Backend.Security.CustomAuthProvider;
import Backend.Security.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
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
      return "redirect:/notes";
      }
  }

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