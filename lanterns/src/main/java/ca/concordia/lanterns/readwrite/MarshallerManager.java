package ca.concordia.lanterns.readwrite;

import java.io.Reader;
import java.io.Writer;

/**
 * Generic Marshaller Manager.
 * @param <T>
 */
public interface MarshallerManager<T> {

	/**
	 * Marshalls the content of an object of type {@link T} into a {@link Writer}.
	 * @param object
	 * @return	The filled {@link Writer}.
	 */
	Writer marshall(T object);
	
	/**
	 * Creates an instance of an object of type {@link T} by unmarshalling the data contained in a {@link Reader}.
	 * @param textRepresentation
	 * @return	The new instance of {@link T}.
	 */
	T unmarshall(Reader textRepresentation);
}
