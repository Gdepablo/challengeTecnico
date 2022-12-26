package Backend.component;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotesDTO implements Serializable {
    //Se usa para evitar revelar el dominio; y es como que yo puedo elegir qué datos mostrar y qué no
    //Por ej si mi nota tiene un Autor,y yo no lo queiro mostrar, entonces en NotasDTO no lo pongo
    //Spring es inteligente entonces las querys que vos creás no las 'declarás' como tal sino que él lo hace por vos.
    //Además acá no van las relaciones porque podría considerarse que esto es un patrón adapter de tu clase. Entonces solo hace de pasamanos.

    int id;
    String title;
    String content;
    Boolean active = true;
    List<Category> categories = new ArrayList<>();

    public boolean estaActiva(Notes note) {
        return note.getActive();
    }

    public void agregarCategoria(Category category) {
        categories.add(category);
    }

    public void quitarCategoria(Category category) {
        categories.remove(category);
    }

    public void agregarMuchasCategorias(List<Category> categories) {
        this.categories.addAll(categories);
    }

    public void quitarMuchasCategorias(List<Category> categories) {
        this.categories.removeAll(categories);
    }
}
