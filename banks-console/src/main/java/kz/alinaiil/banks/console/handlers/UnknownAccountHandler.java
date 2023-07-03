package kz.alinaiil.banks.console.handlers;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.Client;

import java.math.BigDecimal;
import java.util.Scanner;

public class UnknownAccountHandler extends AccountHandler {
    @Override
    public Account handle(Client client, Bank bank, BigDecimal openingBalance, String request, Scanner in) {
        System.out.println("Wrong input");
        return null;
    }
}
