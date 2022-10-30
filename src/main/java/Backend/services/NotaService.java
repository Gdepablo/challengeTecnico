package Backend.services;

import Backend.component.Nota;

import java.util.List;

public interface NotaService {
    Nota getNotasById(int id);
    List<Nota> getAllNotasActivas();
    public void save(Nota nota);
    public void archivarNota(int id);
    public void desarchivarNota(int id);

    public void agregarCategoria(int id, String categoria);

    public void removerCategoria(int id, String categoria);

    //TODO: Averiguar si saveall guarda un elemento tambien o solo dos o mas en una lista
}