package nl.parkhaven.wasschema.model;

public interface CrudDao<E> {

	boolean create(E e);

	E read(E e);

	void update(E e);

	void delete(E e);

	void releaseResources();
}
