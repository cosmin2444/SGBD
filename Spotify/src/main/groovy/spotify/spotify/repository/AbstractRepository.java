package spotify.spotify.repository;

import spotify.spotify.domain.Entity;

import java.util.List;

public interface AbstractRepository<ID,T extends Entity<ID>> {
    public void add(T entity);
    public void delete(ID id);
    public void update(T entity);
    public T findOne(ID id);
    public List<T> findAll();
}
