package kz.alinaiil.banks.console.handlers;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.CentralBank;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

public class UnknownTransactionHandler extends TransactionHandler {
    @Override
    public UUID handle(CentralBank centralBank, Account account, BigDecimal amount, String request, Scanner in) {
        System.out.println("Wrong input.");
        return null;
    }
}
