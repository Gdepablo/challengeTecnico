package Backend.component;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tag;
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore //Para que no haya recursion infinita.
    private List<Note> notes = new ArrayList<>();

}
