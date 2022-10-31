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
    @Column(length = 255)
    String title;
    @Column(length = 5000)
   String content;
   Boolean active = true; // true si está activo, false si está archivado
    @ElementCollection
    List<String> category = new ArrayList<>();

    public Nota(String content, String title) {
        this.content = content;
        this.title = title;


    }

    @Override
    public String toString() {
            return "Educacion{"+ "id=" + id +
                    ", title" + title +
                    ", content=" + content +
                    ", active=" + active +
                    ", category=" + category + " }";
        }

    public Nota getNota() {
        return this;
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

    public void cambiarTitulo(String titulo) {
        this.title = titulo;
    }

    public String getTitulo() {
        return title;
    }

    public void actualizarContenido(String contenido) {
        this.content = contenido;
    }

    public String getContenido() {
        return content;
    }
    public void agregarMuchasCategorias(List<String> categorias) {
     this.category = categorias;
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