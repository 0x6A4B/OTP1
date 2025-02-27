package service;

import java.util.List;
import java.util.Optional;

public interface IManager<T, S> {
    public T read(S s);
    public T create(T t);
    public List<T> readAll(S s);
    public boolean remove(T t);
    public T update(T t);
    public boolean removeAll(T t);
    //public boolean deleteAll(); // for removing all devices using token
}
