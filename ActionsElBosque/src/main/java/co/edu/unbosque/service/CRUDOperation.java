package co.edu.unbosque.service;

import java.util.List;

public interface CRUDOperation<T> {
	
	public void create(T data);
	
	public void deleteById(Long id);
	
	public void updateById(Long id, T data);
	
	public List<T> getAll();
	
	public boolean exist(Long id);

}
