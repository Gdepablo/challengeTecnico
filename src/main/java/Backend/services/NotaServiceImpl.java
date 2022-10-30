package Backend.services;
import Backend.component.Nota;
import org.springframework.transaction.annotation.Transactional;
import Backend.repository.NotasRepository;

import java.util.List;
import java.util.Optional;

public class NotaServiceImpl implements NotaService {
    private NotasRepository notasRepository;

    public NotaServiceImpl(NotasRepository notasRepository) {
        this.notasRepository = notasRepository;
    }


    @Override
    public Nota getNotasById(int id) {
        Optional<Nota> notas = notasRepository.findById(id);
        return notas.stream().toList().get(0); //Pasamanos para sacarme el optional del JPARepository
    }

    @Override
    public List<Nota> getAllNotasActivas() {
        return notasRepository.findAll().stream().filter(nota -> nota.estaActiva()).toList();
    } //Para las archivadas, basta con negar este metodo

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
    public void guardarNota(Nota nota) {
    this.save(nota);
}

    public void borrarNota(int id) {
        Nota nota = getNotasById(id);
        this.delete(nota);
    }

    private void delete(Nota nota) {
        notasRepository.delete(nota);
    }

    //TODO: Hacer una especie de on-click que al momento de clickear el boton sea tipo onClick(save (o delete) a la nota)
}