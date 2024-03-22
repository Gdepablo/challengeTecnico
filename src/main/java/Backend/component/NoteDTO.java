package Backend.component;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Data
@RequiredArgsConstructor
public class NoteDTO implements Serializable {
    private int id;
    private String title;
    private String content;
    private Boolean active = true;
    private List<Category> categories = new ArrayList<>();
}
