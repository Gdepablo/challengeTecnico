package Backend.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    int id;
    private String username;
    private String password;
    private Date accountCreationDate = new Date();
    @OneToMany
    private List<Note> notes = new ArrayList<>();

    public void setAllNotes(List<Note> notes) {
        this.notes.addAll(notes);
    }

}
