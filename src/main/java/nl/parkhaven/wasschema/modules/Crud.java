package nl.parkhaven.wasschema.modules;

public interface Crud<E> {

	boolean create(E e);

	E read(E e);

	boolean update(E e);

	boolean delete(E e);

}
