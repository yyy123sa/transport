package com.realtimestudio.transport.dao;

import java.io.IOException;
import java.util.Map;

public interface CommonDao<T> {
	Map<String, T> findAll();
	T findById(String key);
	void put(String key, T t);
	void delete(String key);
	

}
