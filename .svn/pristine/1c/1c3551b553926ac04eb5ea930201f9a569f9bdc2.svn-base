package com.bourgadix.dao;

import java.util.List;

public interface DaoService {
	public <T> void save(final T o);

	public <T> void update(final T o);

	public <T> List<T> getAll(final Class<T> type);

	public <T> T get(final Class<T> type, final Integer id);

	public <T> List<T> getByProperty(final Class<T> type, String propertyName,
			Object value);

	public List<Visit> getVisits(Integer ud, Integer a);

	public List<Visit> getActifVisits(Integer a, Integer b);
}
