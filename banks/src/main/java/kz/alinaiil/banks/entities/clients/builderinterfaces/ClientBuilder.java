package kz.alinaiil.banks.entities.clients.builderinterfaces;

import kz.alinaiil.banks.interfaces.Client;

/**
 * Builder interface
 * @author alinaiil
 * @version 1.0
 */
public interface ClientBuilder {
    /**
     * Adds address and checks it
     * @param address Address to add
     * @return Builder for another additions or final build
     */
    ClientBuilder withAddress(String address);

    /**
     * Adds passport number and checks it
     * @param passportNumber Passport number to add
     * @return Builder for another additions or final build
     */
    ClientBuilder withPassportNumber(Integer passportNumber);

    /**
     * Method for a final client build
     * @return Returns built client
     */
    Client build();
}
