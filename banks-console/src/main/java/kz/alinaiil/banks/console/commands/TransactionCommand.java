package kz.alinaiil.banks.console.commands;

import kz.alinaiil.banks.console.handlers.*;
import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.CentralBank;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransactionCommand implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public TransactionCommand(CentralBank centralBank, Scanner in)
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
        System.out.println("Enter account id:");
        for (Account a : centralBank.getBank(bankName).getClient(name, surname).getAccounts()) {
            System.out.println(a.getId() + " " + a.getAccountType());
        }
        String id = in.nextLine();
        Account account = centralBank.getBank(bankName).getAccount(id);
        TransactionHandler replenish = new ReplenishHandler();
        TransactionHandler transfer = new TransferHandler();
        TransactionHandler withdraw = new WithdrawHandler();
        TransactionHandler unknown = new UnknownTransactionHandler();
        System.out.println("Enter transaction type (Replenish/Transfer/Withdraw):");
        String type = in.nextLine();
        System.out.println("Enter amount:");
        BigDecimal amount = in.nextBigDecimal();
        in.nextLine();
        replenish.setNext(withdraw).setNext(transfer).setNext(unknown);
        replenish.handle(centralBank, account, amount, type, in);
        System.out.println("Transaction completed");
    }
}
