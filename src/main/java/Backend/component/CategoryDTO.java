package Backend.component;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class CategoryDTO implements Serializable { //No se persiste no es necesario anotaciones de hibernate.

    private int id;
    private String tag;
    private List<Notes> notes = new ArrayList<>();

    //NO LLEVA CONSTRUCTOR, SE OCUPA EL DATA DE LOMBOK. SI LE PONGO CONSTRUCTOR EL NO ARGS CONSTRUCTOR NO LO TOMA.

}
