package kz.alinaiil.banks.console.commands;

import kz.alinaiil.banks.interfaces.CentralBank;

import java.util.Scanner;

public class AddAddress implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public AddAddress(CentralBank centralBank, Scanner in)
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

        System.out.println("Enter bank name:");
        String bankName = in.nextLine();
        System.out.println("Enter client's name:");
        String name = in.nextLine();
        System.out.println("Enter client's surname:");
        String surname = in.nextLine();
        System.out.println("Enter client's address:");
        String address = in.nextLine();
        centralBank.getBank(bankName).getClient(name, surname).addAddress(address);
        System.out.println("Address added");
    }
}
