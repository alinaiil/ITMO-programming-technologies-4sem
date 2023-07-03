package kz.alinaiil.banks.entities.clients;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.entities.clients.builderinterfaces.ClientBuilder;
import kz.alinaiil.banks.entities.clients.builderinterfaces.ClientNameBuilder;
import kz.alinaiil.banks.entities.clients.builderinterfaces.ClientSurnameBuilder;
import kz.alinaiil.banks.interfaces.Client;
import kz.alinaiil.banks.observers.BankObserver;
import kz.alinaiil.banks.observers.ClientNotifier;
import kz.alinaiil.banks.observers.ConsoleNotifier;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Class for the concrete client
 * @author alinaiil
 * @version 1.0
 */
@Getter
@Setter
public class ConcreteClient implements Client, BankObserver {
    private final List<Account> accounts;

    /**
     * Creates concrete client
     * @param name Name of the client
     * @param surname Surname of the client
     * @param address Address of the client
     * @param passportNumber Passport number of the client
     */
    private ConcreteClient(String name, String surname, String address, Integer passportNumber)
    {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passportNumber;
        this.id = UUID.randomUUID();
        accounts = new ArrayList<Account>();
        clientNotifier = new ConsoleNotifier();
    }

    /**
     * Name of the client
     */
    public String name;
    /**
     * Surname of the client
     */
    public String surname;
    /**
     * Address of the client
     */
    public String address;
    /**
     * Passport number of a client
     */
    public Integer passport;
    /**
     * id of the client
     */
    public UUID id;
    /**
     * Client's notifier
     */
    public ClientNotifier clientNotifier;


    /**
     * Returns client builder
     * @return Returns client builder
     */
    public static ClientNameBuilder getBuilder() {
        return new ConcreteClientBuilder();
    }

    @Override
    public boolean isSuspicious() {
        return address == null || passport == null;
    }

    @Override
    public void changeNotifier(ClientNotifier clientNotifier) {
        Objects.requireNonNull(clientNotifier);
        this.clientNotifier = clientNotifier;
    }

    @Override
    public void addAccount(Account account) {
        Objects.requireNonNull(account);
        accounts.add(account);
    }

    @Override
    public void addAddress(String address) {
        this.address = address;
    }

    @Override
    public void addPassport(Integer passport) {
        this.passport = passport;
    }

    @Override
    public void update(String message) {
        clientNotifier.notify(message);
    }

    /**
     * Client builder that builds client step-by-step
     * @author alinaiil
     * @version 1.0
     */
    private static class ConcreteClientBuilder implements ClientNameBuilder, ClientSurnameBuilder, ClientBuilder {
        private String name;
        private String surname;
        private String address = null;
        private Integer passportNumber = null;

        @Override
        public ClientBuilder withAddress(String address) {
            if (address.isBlank()) {
                this.address = null;
                return this;
            }

            this.address = address;
            return this;
        }

        @Override
        public ClientBuilder withPassportNumber(Integer passportNumber) {
            this.passportNumber = passportNumber;
            return this;
        }

        @Override
        public Client build() {
            return new ConcreteClient(name, surname, address, passportNumber);
        }

        @Override
        public ClientSurnameBuilder withName(String name) {
            Objects.requireNonNull(name);
            this.name = name;
            return this;
        }

        @Override
        public ClientBuilder withSurname(String surname) {
            Objects.requireNonNull(surname);
            this.surname = surname;
            return this;
        }
    }
}
