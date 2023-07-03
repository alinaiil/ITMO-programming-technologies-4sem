package kz.alinaiil.banks.models;

import kz.alinaiil.banks.exceptions.BankConditionsException;
import kz.alinaiil.banks.exceptions.BanksException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Checks and stores debit interest
 * @author alinaiil
 * @version 1.0
 */
@Getter
@Setter
public class DebitInterest {
    /**
     * Checks if BigDecimal is suitable to be a debit interest
     * @param debitInterest Interest to check and store
     */
    public DebitInterest(BigDecimal debitInterest) {
        checkInterest(debitInterest);
        interest = debitInterest;
    }

    /**
     * Stores the interest
     */
    public BigDecimal interest;

    /**
     * Changes interest to a new value
     * @param newInterest New value of the interest
     */
    public void changeInterest(BigDecimal newInterest)
    {
        checkInterest(newInterest);
        interest = newInterest;
    }

    /**
     * Checks if BigDecimal is suitable to be an interest
     * @param debitInterest BigDecimal to be checked
     * @throws BanksException Throws exception if the given BigDecimal is invalid
     */
    private void checkInterest(BigDecimal debitInterest) throws BanksException {
        if (debitInterest.compareTo(BigDecimal.valueOf(0)) < 0 || debitInterest.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw BankConditionsException.depositInterestIncorrectInterestException();
        }
    }
}
