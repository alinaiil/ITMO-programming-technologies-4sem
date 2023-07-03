package kz.alinaiil.banks.console.handlers;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.Client;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class DepositAccountHandler extends AccountHandler {
    @Override
    public Account handle(Client client, Bank bank, BigDecimal openingBalance, String request, Scanner in) {
        if (request.equals("Deposit")) {
            System.out.println("Enter term in \"yyyy mm dd\"format");
            int year = in.nextInt();
            int month = in.nextInt();
            int day = in.nextInt();
            in.nextLine();
            Date dueTo = Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
            return bank.createDepositAccount(client, openingBalance, dueTo);
        } else {
            return super.handle(client, bank, openingBalance, request, in);
        }
    }
}
