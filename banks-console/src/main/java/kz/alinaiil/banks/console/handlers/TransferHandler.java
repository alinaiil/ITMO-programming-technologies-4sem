package kz.alinaiil.banks.console.handlers;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.CentralBank;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

public class TransferHandler extends TransactionHandler {
    @Override
    public UUID handle(CentralBank centralBank, Account account, BigDecimal amount, String request, Scanner in) {
        if (request.equals("Transfer")) {
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
            Account receiver = centralBank.getBank(bankName).getAccount(id);
            return centralBank.transfer(account, receiver, amount);
        } else {
            return super.handle(centralBank, account, amount, request, in);
        }
    }
}
