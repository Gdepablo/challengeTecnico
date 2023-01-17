package Backend.component;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class UserPrincipal implements Serializable , UserDetails {
    int id;
    private String username;
    private String password;
    private Date accountCreationDate = new Date(); //Esta clase es solo para el login, no necesito la lista de notas.


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    public void setNotes(List<Note> notes) {
    }

    public boolean getNotes() {
        return false;
    }

    public String getName() {
        return "notes";

    }
}
