package it.gspRiva.manager;

import java.io.Serializable;

public interface CrudOperation<T> extends Serializable {
	 boolean create(T t);
	 boolean update(T t);
	 boolean delete(Integer id);
	 boolean slim_delete(Integer id);
}
