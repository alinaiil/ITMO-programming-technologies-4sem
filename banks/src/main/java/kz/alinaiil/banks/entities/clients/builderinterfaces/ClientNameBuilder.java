package kz.alinaiil.banks.entities.clients.builderinterfaces;

/**
 * Builder interface
 * @author alinaiil
 * @version 1.0
 */
public interface ClientNameBuilder {
    /**
     * Adds and checks name
     * @param name Name to add
     * @return Builder for surname addition
     */
    ClientSurnameBuilder withName(String name);
}
