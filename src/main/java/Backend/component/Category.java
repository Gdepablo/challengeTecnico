package Backend.component;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tag;
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore //Para que no haya recursion infinita.
    private List<Note> notes = new ArrayList<>();

    public Category(String tag) {
        this.tag = tag;
    }

}
