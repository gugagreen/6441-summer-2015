package ca.concordia.lanterns.marshalling;

/**
 * Generic Marshaller Manager.
 * @param <T>
 */
public interface Marshaller<T> {

	void marshall(T object);
}
