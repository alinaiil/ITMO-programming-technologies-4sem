package kz.alinaiil.banks.console.commands;

import kz.alinaiil.banks.console.handlers.*;
import kz.alinaiil.banks.interfaces.CentralBank;

import java.math.BigDecimal;
import java.util.Scanner;

public class CreateAccount implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public CreateAccount(CentralBank centralBank, Scanner in)
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
        System.out.println("Enter opening balance:");
        BigDecimal openingBalance = in.nextBigDecimal();
        in.nextLine();
        AccountHandler credit = new CreditAccountHandler();
        AccountHandler debit = new DebitAccountHandler();
        AccountHandler deposit = new DepositAccountHandler();
        AccountHandler unknown = new UnknownAccountHandler();
        System.out.println("Enter account type (Credit/Debit/Deposit):");
        String type = in.nextLine();
        credit.setNext(debit).setNext(deposit).setNext(unknown);
        credit.handle(centralBank.getBank(bankName).getClient(name, surname), centralBank.getBank(bankName), openingBalance, type, in);
        System.out.println("Account was successfully created");
    }
}
