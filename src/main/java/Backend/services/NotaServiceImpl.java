package Backend.services;
import Backend.component.Categoria;
import Backend.component.Nota;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Backend.repository.NotasRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotaServiceImpl implements NotaService {
    //Es para abstracción, entiendo, que se hace esto de que hereda de interfaz, ademas de por tipos. TL; DR polimorfismo al adaptar una interfaz.
    private NotasRepository notasRepository;

    public void agregarNota(NotasDTO nota) {
        this.save(nota);
    }
    @Override
    public Nota getNotasById(int id) {
        Optional<Nota> notas = notasRepository.findById(id);
        return notas.stream().toList().get(0); //Pasamanos para sacarme el optional del JPARepository
    }

    @Override
    public List<Nota> getAllNotasActivas() {
        return notasRepository.findAll().stream().filter(nota -> nota.getActive()).toList();
    }

    public List<Nota> getAllNotasArchivadas() {
        return notasRepository.findAll().stream().filter(nota -> !nota.getActive()).toList();
    } //Entiendo que es un code smell pero no se me ocurre cómo arreglarlo


    @Override
    public void archivarNota(int id) {
        Nota nota = getNotasById(id);
         nota.setActive(false);
    }

    public void desarchivarNota(int id) {
        Nota nota = getNotasById(id);
        nota.setActive(true); //TODO: En el model, lo que deberíamos hacer es filtrar por activas y eso parecido a lo de vinculaciones del TP de DDS.
    }

    @Override
    public void agregarCategoria(int id, String categoria) {

    }

    @Override
    public void removerCategoria(int id, String categoria) {

    }

    @Override
    public Optional<Nota> findByNotasId(int id) {
        return Optional.empty();
    }

    @Transactional
    public void save(NotasDTO nota) {

    }

    @Override
    public void saveAll(List<NotasDTO> nota) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public boolean estaActiva(Nota nota) {
        return false;
    }

    @Transactional
    public void agregarCategoria(int id, Categoria categoria) {
        Nota nota = getNotasById(id);
        nota.agregarCategoria(categoria);
    }
    @Transactional
    public void removerCategoria(int id, Categoria categoria) {
        Nota nota = getNotasById(id);
        nota.quitarCategoria(categoria);
    }

    @Transactional
    public void borrarNota(int id) {
        Nota nota = getNotasById(id);
        this.delete(nota);
    }
    @Transactional
    public void actualizarNota(@NotNull Nota nuevaNota) {
        Nota nota = this.getNotasById(nuevaNota.getId());
        if (nota != null) {
            nota.setTitle(nuevaNota.getTitle());
            nota.setContent(nuevaNota.getContent());
            nota.agregarMuchasCategorias(nuevaNota.verCategorias());        }

        else {
            nota = new Nota(nuevaNota.getTitle(), nuevaNota.getContent());
            nota.agregarMuchasCategorias(nuevaNota.verCategorias()); //TODO: Arreglar tema categorias que ahora ya no son strings
        }

        notasRepository.save(nota);
    }
    @Transactional
    public void delete(Nota nota) {
        notasRepository.delete(nota);
    }
}