package Backend.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json");

    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("status", "success");
    responseBody.put("message", "Authentication successful");

    objectMapper.writeValue(response.getWriter(), responseBody);
  }
}