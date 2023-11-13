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

    public NoteDTO(int id, String title, String content, Boolean active, List<String> categories ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.active = active;
        List<String> categoryNames = categories;

// Usar stream y map para crear instancias de Category
        this.categories = categoryNames.stream()
            .map(Category::new) // Utiliza un constructor de Category que acepta un String
            .toList();
    }

    //El método con list de categories (o list de JSON), lo brinda el Data de lombok. Mientras que yo uso
    //overload para poder usar un list de strings, que es lo que recibo del front. Lo podría hacer para recibir
    //una lista de JSON, pero ni ganas. Al final sí hice que le pasara una lista de JSON de categorias para probar el
    //Nested form builder, así que bueno, queda para la posteridad.
}
