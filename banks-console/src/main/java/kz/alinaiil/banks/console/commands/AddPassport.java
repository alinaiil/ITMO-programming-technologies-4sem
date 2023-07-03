package kz.alinaiil.banks.console.commands;

import kz.alinaiil.banks.interfaces.CentralBank;

import java.util.Scanner;

public class AddPassport implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public AddPassport(CentralBank centralBank, Scanner in)
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
        System.out.println("Enter client's passport:");
        int passport = in.nextInt();
        in.nextLine();
        centralBank.getBank(bankName).getClient(name, surname).addPassport(passport);
        System.out.println("Passport added");
    }
}
