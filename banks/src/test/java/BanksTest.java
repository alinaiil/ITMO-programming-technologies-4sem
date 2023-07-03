import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.entities.banks.ConcreteCentralBank;
import kz.alinaiil.banks.entities.clients.ConcreteClient;
import kz.alinaiil.banks.exceptions.AccountException;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.CentralBank;
import kz.alinaiil.banks.interfaces.Client;
import kz.alinaiil.banks.models.CreditConditions;
import kz.alinaiil.banks.models.DebitInterest;
import kz.alinaiil.banks.models.DepositInterest;
import kz.alinaiil.banks.models.SuspiciousClientLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class BanksTest {
    private final CentralBank centralBank = new ConcreteCentralBank();

    @Test
    void testBreakingSuspiciousClientLimit_GetException() {
        Bank bank = centralBank.addBank(
                "Bank",
                new DebitInterest(BigDecimal.valueOf(8)),
                Arrays.asList(new DepositInterest(BigDecimal.valueOf(0), BigDecimal.valueOf(50000), BigDecimal.valueOf(7)),
                        new DepositInterest(BigDecimal.valueOf(50000), null, BigDecimal.valueOf(14))),
                new CreditConditions(BigDecimal.valueOf(200000), BigDecimal.valueOf(100)),
                new SuspiciousClientLimit(BigDecimal.valueOf(10000)));
        Client client = ConcreteClient.getBuilder().withName("Alina").withSurname("Ilyassova").build();
        bank.addClient(client);
        Account account = bank.createDebitAccount(client, BigDecimal.valueOf(100000));
        Assertions.assertThrows(AccountException.class, () -> centralBank.withdraw(account, BigDecimal.valueOf(100000)));
    }

    @Test
    void testBreakingCreditLimitAndDepositTerm_GetException() {
        Bank bank = centralBank.addBank(
                "Bank",
                new DebitInterest(BigDecimal.valueOf(8)),
                Arrays.asList(new DepositInterest(BigDecimal.valueOf(0), BigDecimal.valueOf(50000), BigDecimal.valueOf(7)),
                        new DepositInterest(BigDecimal.valueOf(50000), null, BigDecimal.valueOf(14))),
                new CreditConditions(BigDecimal.valueOf(200000), BigDecimal.valueOf(100)),
                new SuspiciousClientLimit(BigDecimal.valueOf(10000)));
        Client client = ConcreteClient.getBuilder().withName("Alina").withSurname("Ilyassova").build();
        bank.addClient(client);
        Account account1 = bank.createDepositAccount(client, BigDecimal.valueOf(100000), Date.from(LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Account account2 = bank.createCreditAccount(client, BigDecimal.valueOf(10));
        Assertions.assertThrows(AccountException.class, () -> centralBank.transfer(account2, account1, BigDecimal.valueOf(300000)));
        Assertions.assertThrows(AccountException.class, () -> centralBank.transfer(account1, account2, BigDecimal.valueOf(30)));
    }

    @Test
    void testGoToTheFuture_GetNewBalance() {
        Bank bank = centralBank.addBank(
                "Bank",
                new DebitInterest(BigDecimal.valueOf(8)),
                Arrays.asList(new DepositInterest(BigDecimal.valueOf(0), BigDecimal.valueOf(50000), BigDecimal.valueOf(7)),
                        new DepositInterest(BigDecimal.valueOf(50000), null, BigDecimal.valueOf(8))),
                new CreditConditions(BigDecimal.valueOf(200000), BigDecimal.valueOf(100)),
                new SuspiciousClientLimit(BigDecimal.valueOf(10000)));
        Client client = ConcreteClient.getBuilder().withName("Alina").withSurname("Ilyassova").build();
        bank.addClient(client);
        Account account1 = bank.createDebitAccount(client, BigDecimal.valueOf(100000));
        Account account2 = bank.createDepositAccount(client, BigDecimal.valueOf(100000), Date.from(LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        centralBank.addDays(30);

        BigDecimal balanceDebit = BigDecimal.valueOf(100000);
        BigDecimal balanceDeposit = BigDecimal.valueOf(100000);
        BigDecimal interestDebit = BigDecimal.valueOf(0);
        BigDecimal interestDeposit = BigDecimal.valueOf(0);
        BigDecimal interestValueDebit = BigDecimal.valueOf(8);
        BigDecimal interestValueDeposit = BigDecimal.valueOf(8);
        interestValueDebit = interestValueDebit.divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);
        interestValueDeposit = interestValueDeposit.divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);

        for (int i = 0; i < 30; i++)
        {
            interestDebit = interestDebit.add(balanceDebit.multiply(interestValueDebit));
            interestDeposit = interestDeposit.add(balanceDeposit.multiply(interestValueDeposit));
        }

        balanceDebit = balanceDebit.add(interestDebit);
        balanceDeposit = balanceDeposit.add(interestDeposit);

        Assertions.assertEquals(account1.getBalance(), balanceDebit);
        Assertions.assertEquals(account2.getBalance(), balanceDeposit);
    }

    @Test
    void testCancelTransaction_TransactionCancelled() {
        Bank bank = centralBank.addBank(
                "Bank",
                new DebitInterest(BigDecimal.valueOf(8)),
                Arrays.asList(new DepositInterest(BigDecimal.valueOf(0), BigDecimal.valueOf(50000), BigDecimal.valueOf(7)),
                        new DepositInterest(BigDecimal.valueOf(50000), null, BigDecimal.valueOf(14))),
                new CreditConditions(BigDecimal.valueOf(200000), BigDecimal.valueOf(100)),
                new SuspiciousClientLimit(BigDecimal.valueOf(10000)));
        Client client = ConcreteClient.getBuilder().withName("Alina").withSurname("Ilyassova").build();
        bank.addClient(client);
        Account account1 = bank.createDebitAccount(client, BigDecimal.valueOf(100000));
        Account account2 = bank.createCreditAccount(client, BigDecimal.valueOf(10));
        UUID transactionId = centralBank.transfer(account2, account1, BigDecimal.valueOf(1000));
        BigDecimal expectedCreditBalance = BigDecimal.valueOf(10 - 1000 - 100);
        Assertions.assertEquals(expectedCreditBalance, account2.getBalance());
        centralBank.cancelTransaction(transactionId);
        expectedCreditBalance = expectedCreditBalance.add(BigDecimal.valueOf(1000 + 100));
        Assertions.assertEquals(expectedCreditBalance, account2.getBalance());
    }

    @Test
    void testChangeInterestAndGoToTheFuture_GetNewBalance() {
        Bank bank = centralBank.addBank(
                "Bank",
                new DebitInterest(BigDecimal.valueOf(8)),
                Arrays.asList(new DepositInterest(BigDecimal.valueOf(0), BigDecimal.valueOf(50000), BigDecimal.valueOf(7)),
                        new DepositInterest(BigDecimal.valueOf(50000), null, BigDecimal.valueOf(14))),
                new CreditConditions(BigDecimal.valueOf(200000), BigDecimal.valueOf(100)),
                new SuspiciousClientLimit(BigDecimal.valueOf(10000)));
        Client client = ConcreteClient.getBuilder().withName("Alina").withSurname("Ilyassova").build();
        bank.addClient(client);
        Account account1 = bank.createDebitAccount(client, BigDecimal.valueOf(100000));

        centralBank.addDays(12);
        bank.changeDebitInterest(BigDecimal.valueOf(10));
        centralBank.addDays(18);

        BigDecimal balanceDebit = BigDecimal.valueOf(100000);
        BigDecimal interestDebit = BigDecimal.valueOf(0);
        BigDecimal interestValueDebit = BigDecimal.valueOf(8);
        interestValueDebit = interestValueDebit.divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);

        for (int i = 0; i < 12; i++) {
            interestDebit = interestDebit.add(balanceDebit.multiply(interestValueDebit));
        }

        BigDecimal newInterestValueDebit = BigDecimal.valueOf(10);
        newInterestValueDebit = newInterestValueDebit.divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);

        for (int i = 0; i < 18; i++) {
            interestDebit = interestDebit.add(balanceDebit.multiply(newInterestValueDebit));
        }

        balanceDebit = balanceDebit.add(interestDebit);

        Assertions.assertEquals(account1.getBalance(), balanceDebit);
    }
}
