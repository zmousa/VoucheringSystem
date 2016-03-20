package com.jee.api.dao;

import java.util.List;

public interface HelperDao {

	Object save(Object o) throws Exception;

	Object update(Object o) throws Exception;

	void delete(Object o) throws Exception;

	Filter entity(Class<?> clazz) throws Exception;
	
	List<?> search(String querySql) throws Exception;

	void executeUpdate(String querSql);
	
}
