package Backend.repository;

import Backend.component.Nota;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class RepositoryAdapter implements NotasRepository {


    @Override
    public List<Nota> findAll() {
        return null;
    }

    @Override
    public List<Nota> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Nota> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Nota> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Nota entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Nota> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Nota> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Nota> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Nota> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Nota> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Nota> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Nota> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Nota getOne(Integer integer) {
        return null;
    }

    @Override
    public Nota getById(Integer integer) {
        return null;
    }

    @Override
    public Nota getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Nota> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Nota> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Nota> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Nota> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Nota> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Nota> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Nota, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
