package Backend.component;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    int id;
    String tag;
    @Column(length = 5000)
   String content;
   Boolean active = true; // true si está activo, false si está archivado
    @ElementCollection
    List<String> category = new ArrayList<>();

    public Nota(String tag, String content) {
        this.tag = tag;
        this.content = content;


    }

    public void agregarCategoria(String categoria) {
        category.add(categoria);
    }

    public void quitarCategoria(String categoria) {
        category.remove(categoria);
    }

    public List<String> verCategorias() {
        return this.category;
    }

    public int getId() {
        return id;
    }
    public void archivarNota() {
        this.active = false;
    }
    public void desarchivarNota() {
        this.active = true;
    }

    public boolean estaActiva() {
        return active;
    }
}