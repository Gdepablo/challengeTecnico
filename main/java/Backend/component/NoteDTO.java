package Backend.component;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class NoteDTO implements Serializable {

    private int id;
    private String title;
    private String content;
    private Boolean active = true;
    private List<Category> categories = new ArrayList<>();
}
