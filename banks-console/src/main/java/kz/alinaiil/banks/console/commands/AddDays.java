package kz.alinaiil.banks.console.commands;

import kz.alinaiil.banks.interfaces.CentralBank;

import java.util.Scanner;

public class AddDays implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public AddDays(CentralBank centralBank, Scanner in)
    {
        this.centralBank = centralBank;
        this.in = in;
    }

    @Override
    public void execute() {
        if (centralBank == null)
        {
            System.out.println("Please, create central bank first");
            return;
        }

        System.out.println("Enter number of days:");
        int days = in.nextInt();
        in.nextLine();
        centralBank.addDays(days);
        System.out.println(days + " days added");
    }
}
