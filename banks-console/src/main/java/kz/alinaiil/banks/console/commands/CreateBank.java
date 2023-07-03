package kz.alinaiil.banks.console.commands;

import kz.alinaiil.banks.interfaces.CentralBank;
import kz.alinaiil.banks.models.CreditConditions;
import kz.alinaiil.banks.models.DebitInterest;
import kz.alinaiil.banks.models.DepositInterest;
import kz.alinaiil.banks.models.SuspiciousClientLimit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateBank implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public CreateBank(CentralBank centralBank, Scanner in)
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
        String name = in.nextLine();
        System.out.println("Enter debit interest:");
        DebitInterest debitInterest = new DebitInterest(in.nextBigDecimal());
        in.nextLine();
        System.out.println("Enter number of deposit interests:");
        int numberDeposit = in.nextInt();
        in.nextLine();
        List<DepositInterest> depositInterests = new ArrayList<>();
        for (int i = 0; i < numberDeposit; i++) {
            System.out.println("Deposit interest #" + (i + 1));
            System.out.println("Enter lower bound:");
            BigDecimal lower = in.nextBigDecimal();
            in.nextLine();
            System.out.println("Enter upper bound or whitespace if there is no upper bound:");
            String upperOrNull = in.nextLine();
            BigDecimal upper;
            if (upperOrNull.isBlank()) {
                upper = null;
            } else {
                upper = new BigDecimal(upperOrNull);
            }

            System.out.println("Enter interest for given bounds:");
            BigDecimal interest = in.nextBigDecimal();
            in.nextLine();
            depositInterests.add(new DepositInterest(lower, upper, interest));
        }

        System.out.println("Enter credit limit:");
        BigDecimal creditLimit = in.nextBigDecimal();
        in.nextLine();
        System.out.println("Enter credit fee:");
        BigDecimal creditFee = in.nextBigDecimal();
        in.nextLine();
        CreditConditions creditConditions = new CreditConditions(creditLimit, creditFee);
        System.out.println("Enter suspicious client limit");
        SuspiciousClientLimit suspiciousClientLimit = new SuspiciousClientLimit(in.nextBigDecimal());
        in.nextLine();

        centralBank.addBank(name, debitInterest, depositInterests, creditConditions, suspiciousClientLimit);
        System.out.println("Bank " + name + " was successfully created");
    }
}
