package service;

import java.util.List;

public interface IManager<T, S> {
    public T read(S s);
    public T create(T t);
    public List<T> readAll(S s);
    public boolean remove(T t);
}
