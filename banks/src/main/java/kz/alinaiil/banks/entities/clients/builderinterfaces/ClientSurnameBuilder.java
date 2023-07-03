package kz.alinaiil.banks.entities.clients.builderinterfaces;

/**
 * Builder interface
 * @author alinaiil
 * @version 1.0
 */
public interface ClientSurnameBuilder {
    /**
     * Adds and checks surname
     * @param surname Surname to add
     * @return Builder for another additions or final build
     */
    ClientBuilder withSurname(String surname);
}
