package Backend.services;

import Backend.component.Categoria;
import Backend.component.Nota;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface NotaService {
    Nota getNotasById(int id);
    List<Nota> getAllNotasActivas(); // findall
    public void archivarNota(int id);
    public void desarchivarNota(int id);

    public void agregarCategoria(int id, String categoria); //TODO: Editar esto despues

    public void removerCategoria(int id, String categoria);

    Optional<Nota> findByNotasId(int id);

    void save(NotasDTO nota);

    void saveAll(List<NotasDTO> nota);

    void deleteById(int id);

    public boolean estaActiva(Nota nota);


    //Tenemos que pensar en la nota sólo como almacenamiento, igual que el DTO es un objeto de transición para no revelar
    //Mi dominio (o sea cómo modelé la nota en este caso) al usuario o a algún vivo. Entonces por eso usamos el DTO y por eso
    //es que hacemos que el repositorio se encargue de toodo.

}