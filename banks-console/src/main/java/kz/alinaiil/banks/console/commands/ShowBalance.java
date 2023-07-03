package kz.alinaiil.banks.console.commands;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.CentralBank;

import java.util.Scanner;

public class ShowBalance implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public ShowBalance(CentralBank centralBank, Scanner in) {
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
        System.out.println("Enter account id:");
        for (Account a : centralBank.getBank(bankName).getClient(name, surname).getAccounts()) {
            System.out.println(a.getId() + " " + a.getAccountType());
        }
        String id = in.nextLine();
        Account account = centralBank.getBank(bankName).getAccount(id);
        System.out.println(account.getBalance());
    }
}
