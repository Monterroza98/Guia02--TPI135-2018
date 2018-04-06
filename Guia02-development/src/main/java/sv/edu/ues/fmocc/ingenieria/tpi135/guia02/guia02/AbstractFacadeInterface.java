/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.ues.fmocc.ingenieria.tpi135.guia02.guia02;

import java.util.List;

/**
 *
 * @author ale
 * @param <T>
 */
public interface AbstractFacadeInterface <T> {
    
    T create(T entity);

    T edit(T entity);

    T remove(T entity);

    boolean crear (T entity);
    
    boolean modificar (T entity);
    
    boolean eliminar (T entity);
    
    T find(Object id);

    List<T> findAll();

    List<T> findRange(int[] range);

    int count();
    
    
    
}
