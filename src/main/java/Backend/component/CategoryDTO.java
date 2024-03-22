package Backend.component;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class CategoryDTO implements Serializable {
    private int id;
    private String tag;
    private List<Backend.component.Note> notes = new ArrayList<>();
}
