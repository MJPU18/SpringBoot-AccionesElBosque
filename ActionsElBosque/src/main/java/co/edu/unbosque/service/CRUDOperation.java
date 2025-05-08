package co.edu.unbosque.service;

import java.util.List;

public interface CRUDOperation<T> {
	
	public T create(T data);
	
	public T deleteById(Long id);
	
	public T updateById(Long id, T data);
	
	public List<T> getAll();
	
	public boolean exist(Long id);

}
