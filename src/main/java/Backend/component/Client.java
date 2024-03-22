package Backend.component;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    int id;
    @Size(max = 50)
    @Column(unique = true)
    private String username;
    @Size(max = 100)
    private String password;
    private Date accountCreationDate = new Date();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    public void setAllNotes(List<Note> notes) {
        this.notes.addAll(notes);
    }
}
