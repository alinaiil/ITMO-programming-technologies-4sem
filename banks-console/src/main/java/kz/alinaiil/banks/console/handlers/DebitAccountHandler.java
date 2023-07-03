package kz.alinaiil.banks.console.handlers;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.Client;

import java.math.BigDecimal;
import java.util.Scanner;

public class DebitAccountHandler extends AccountHandler {
    @Override
    public Account handle(Client client, Bank bank, BigDecimal openingBalance, String request, Scanner in) {
        if (request.equals("Debit")) {
            return bank.createDebitAccount(client, openingBalance);
        } else {
            return super.handle(client, bank, openingBalance, request, in);
        }
    }
}
