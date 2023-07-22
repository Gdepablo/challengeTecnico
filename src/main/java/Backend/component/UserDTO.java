package Backend.component;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    int id;
    private String username;
    private String password;
    private List<Note> notes = new ArrayList<>();
    private Date accountCreationDate = new Date();
}
