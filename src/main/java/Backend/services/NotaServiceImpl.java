package Backend.services;
import Backend.component.Nota;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;
import Backend.repository.NotasRepository;

import java.util.List;
import java.util.Optional;

public class NotaServiceImpl implements NotaService {
    private NotasRepository notasRepository;

    public NotaServiceImpl(NotasRepository notasRepository) {
        this.notasRepository = notasRepository;
    }


    public void agregarNota(Nota nota) {
        this.save(nota);
    }
    @Override
    public Nota getNotasById(int id) {
        Optional<Nota> notas = notasRepository.findById(id);
        return notas.stream().toList().get(0); //Pasamanos para sacarme el optional del JPARepository
    }

    @Override
    public List<Nota> getAllNotasActivas() {
        return notasRepository.findAll().stream().filter(nota -> nota.estaActiva()).toList();
    }

    public List<Nota> getAllNotasArchivadas() {
        return notasRepository.findAll().stream().filter(nota -> !nota.estaActiva()).toList();
    } //Entiendo que es un code smell pero no se me ocurre cómo arreglarlo

    @Transactional
    public void save(Nota nota) { notasRepository.save(nota);}

    @Override
    public void archivarNota(int id) {
        Nota nota = getNotasById(id);
         nota.archivarNota();
    }

    public void desarchivarNota(int id) {
        Nota nota = getNotasById(id);
        nota.desarchivarNota();
    }

    public void agregarCategoria(int id, String categoria) {
        Nota nota = getNotasById(id);
        nota.agregarCategoria(categoria);
    }

    public void removerCategoria(int id, String categoria) {
        Nota nota = getNotasById(id);
        nota.quitarCategoria(categoria);
    }


    public void borrarNota(int id) {
        Nota nota = getNotasById(id);
        this.delete(nota);
    }

    public void actualizarNota(@NotNull Nota nuevaNota) {
        Nota nota = this.getNotasById(nuevaNota.getId());
        if (nota != null) {
            nota.cambiarTitulo(nuevaNota.getTitulo());
            nota.actualizarContenido(nuevaNota.getContenido());
            nota.agregarMuchasCategorias(nuevaNota.verCategorias());        }

        else {
            nota = new Nota(nuevaNota.getTitulo(), nuevaNota.getContenido());
            nota.agregarMuchasCategorias(nuevaNota.verCategorias());
        }

        notasRepository.save(nota);
    }

    private void delete(Nota nota) {
        notasRepository.delete(nota);
    }
}