package kz.alinaiil.banks.console.commands;

import kz.alinaiil.banks.interfaces.CentralBank;

import java.util.Scanner;

public class ShowDate implements Command {
    private final CentralBank centralBank;

    public ShowDate(CentralBank centralBank) {
        this.centralBank = centralBank;
    }

    @Override
    public void execute() {
        if (centralBank == null)
        {
            System.out.println("Please, create central bank first");
            return;
        }

        System.out.println("Current date is: " + centralBank.getClock().getCurrentTime());
    }
}
