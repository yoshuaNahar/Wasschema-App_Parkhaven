package nl.parkhaven.model.abstraction;

public interface CrudDao<E> {

	boolean create(E e);

	E read(E e);

	void update(E e);

	void delete(E e);
}
