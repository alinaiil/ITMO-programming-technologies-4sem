package kz.alinaiil.banks.console.commands;

import kz.alinaiil.banks.entities.clients.ConcreteClient;
import kz.alinaiil.banks.entities.clients.builderinterfaces.ClientBuilder;
import kz.alinaiil.banks.entities.clients.builderinterfaces.ClientNameBuilder;
import kz.alinaiil.banks.entities.clients.builderinterfaces.ClientSurnameBuilder;
import kz.alinaiil.banks.interfaces.CentralBank;
import kz.alinaiil.banks.interfaces.Client;

import java.util.Scanner;

public class CreateClient implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public CreateClient(CentralBank centralBank, Scanner in)
    {
        this.centralBank = centralBank;
        this.in = in;
    }

    @Override
    public void execute() {
        if (centralBank == null) {
            System.out.println("Please, create central bank first");
            return;
        }

        ClientNameBuilder clientName = ConcreteClient.getBuilder();
        System.out.println("Enter bank name:");
        String bankName = in.nextLine();
        System.out.println("Enter client's name:");
        String name = in.nextLine();
        ClientSurnameBuilder clientSurname = clientName.withName(name);
        System.out.println("Enter client's surname:");
        String surname = in.nextLine();
        ClientBuilder client = clientSurname.withSurname(surname);
        System.out.println("Enter client's address (or whitespace if you don't want to set address):");
        String address = in.nextLine();
        client = client.withAddress(address);
        System.out.println("Enter client's passport (or whitespace if you don't want to set passport):");
        String passport = in.nextLine();
        if (passport.isBlank()) {
            client = client.withPassportNumber(null);
        } else {
            client = client.withPassportNumber(Integer.parseInt(passport));
        }
        Client createdClient = client.build();
        centralBank.getBank(bankName).addClient(createdClient);
        System.out.println("Client " + name + " " + surname + " was created successfully");
    }
}
