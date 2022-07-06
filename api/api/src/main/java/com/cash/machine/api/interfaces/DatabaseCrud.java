package com.cash.machine.api.interfaces;

import java.util.List;


//interface criada para reaproveitamento de codigo, passando valor generico x 
public interface DatabaseCrud<X> {
    
    public abstract int insert(X value);

    public abstract X update(X value);

    public abstract void delete(int id);

    public abstract List<X> getAll();
    
    public abstract X getDataByID(int id);

}
