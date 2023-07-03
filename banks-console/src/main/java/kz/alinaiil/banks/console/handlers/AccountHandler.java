package kz.alinaiil.banks.console.handlers;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.Client;

import java.math.BigDecimal;
import java.util.Scanner;

public abstract class AccountHandler {
    private AccountHandler next;

    public AccountHandler setNext(AccountHandler handler) {
        next = handler;
        return handler;
    }

    public Account handle(Client client, Bank bank, BigDecimal openingBalance, String request, Scanner in) {
        return next.handle(client, bank, openingBalance, request, in);
    }
}
