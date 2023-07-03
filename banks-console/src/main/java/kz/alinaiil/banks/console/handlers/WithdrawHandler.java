package kz.alinaiil.banks.console.handlers;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.CentralBank;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

public class WithdrawHandler extends TransactionHandler {
    @Override
    public UUID handle(CentralBank centralBank, Account account, BigDecimal amount, String request, Scanner in) {
        if (request.equals("Withdraw")) {
            return centralBank.withdraw(account, amount);
        } else {
            return super.handle(centralBank, account, amount, request, in);
        }
    }
}
