package Backend.component;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    int id;
    private String username;
    @OneToMany
    private List<Note> notes = new ArrayList<>();
}
