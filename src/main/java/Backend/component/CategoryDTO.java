package Backend.component;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Data
public class CategoryDTO implements Serializable { //No se persiste no es necesario anotaciones de hibernate.

    Long id;
    String tag;

    public CategoryDTO(String tag) {
        this.tag = tag;
    }

}
