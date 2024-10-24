package dat.lyngby.daos;

import java.util.List;

public interface IDAO <T,I>{
    T create(T t);
    T getById(int id);
    T update(I i,T n);
    void delete(int id);

    List<T> getAll();
}
