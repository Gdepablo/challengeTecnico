package Backend.component;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotesDTO implements Serializable {
    //Se usa para evitar revelar el dominio; y es como que yo puedo elegir qué datos mostrar y que no
    //Por ej si mi nota tiene un Autor,y yo no lo queiro mostrar, entonces en NotasDTO no lo pongo
    //Spring es inteligente entonces las querys que vos creas no las 'declaras' como tal sino que el lo hace por vos.
    //Ademas aca no van las relaciones porque podría considerarse que esto es un patron adapter de tu clase. Entonces solo hace de pasamanos.

    private int id;
    private String title;
    private String content;
    private Boolean active = true;
    private List<Category> categories = new ArrayList<>();
}
