package kz.alinaiil.banks.console;

import kz.alinaiil.banks.console.commands.*;
import kz.alinaiil.banks.entities.banks.ConcreteCentralBank;
import kz.alinaiil.banks.interfaces.CentralBank;

import java.util.Scanner;

public class Console {
    public static void main(String[] args) {
        Command command;
        CentralBank centralBank = null;
        Scanner in = new Scanner(System.in);


        while(true) {
            switch (in.nextLine()) {
                case "-help":
                    command = new Help();
                    command.execute();
                    break;
                case "-createCentralBank":
                    centralBank = new ConcreteCentralBank();
                    System.out.println("Central bank was successfully created");
                    break;
                case "-showDate":
                    command = new ShowDate(centralBank);
                    command.execute();
                    break;
                case "-createBank":
                    command = new CreateBank(centralBank, in);
                    command.execute();
                    break;
                case "-createClient":
                    command = new CreateClient(centralBank, in);
                    command.execute();
                    break;
                case "-createAccount":
                    command = new CreateAccount(centralBank, in);
                    command.execute();
                    break;
                case "-addDays":
                    command = new AddDays(centralBank, in);
                    command.execute();
                    break;
                case "-transaction":
                    command = new TransactionCommand(centralBank, in);
                    command.execute();
                    break;
                case "-showBalance":
                    command = new ShowBalance(centralBank, in);
                    command.execute();
                    break;
                case "-addAddress":
                    command = new AddAddress(centralBank, in);
                    command.execute();
                    break;
                case "-addPassport":
                    command = new AddPassport(centralBank, in);
                    command.execute();
                    break;
                case "-stop":
                    command = new StopConsole();
                    command.execute();
                    return;
                default:
                    command = new Unknown();
                    command.execute();
                    break;
            }
        }
    }
}
