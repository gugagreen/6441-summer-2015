package ca.concordia.lanterns.readwrite;

import java.io.Reader;
import java.io.Writer;

/**
 * Generic Marshaller Manager.
 *
 * @param <T> object type.
 */
public interface MarshallerManager<T> {

    /**
     * Marshalls the content of an object of type {@link T} into a {@link Writer}.
     *
     * @param object to be read
     * @param writer the {@link Writer} to be filled.
     */
    void marshall(final T object, final Writer writer);

    /**
     * Creates an instance of an object of type {@link T} by unmarshalling the data contained in a {@link Reader}.
     *
     * @param textRepresentation text representation of the game.
     * @return The new instance of {@link T}.
     */
    T unmarshall(final Reader textRepresentation);
}
