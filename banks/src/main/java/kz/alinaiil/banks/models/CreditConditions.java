package kz.alinaiil.banks.models;

import kz.alinaiil.banks.exceptions.BanksException;
import kz.alinaiil.banks.exceptions.BankConditionsException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Checks and stores credit conditions
 * @author alinaiil
 * @version 1.0
 */
@Getter
@Setter
public class CreditConditions {
    /**
     * Checks if BigDecimals are suitable to be the credit conditions
     * @param creditLimit Credit limit to check and store
     * @param creditFee Credit fee to check and store
     */
    public CreditConditions(BigDecimal creditLimit, BigDecimal creditFee)
    {
        checkCreditLimit(creditLimit);
        checkCreditFee(creditFee);
        this.creditLimit = creditLimit;
        this.creditFee = creditFee;
    }

    /**
     * Stores credit limit
     */
    public BigDecimal creditLimit;
    /**
     * Stores credit fee
     */
    public BigDecimal creditFee;

    /**
     * Changes credit limit to a new value
     * @param newCreditLimit New value of a credit limit
     */
    public void changeCreditLimit(BigDecimal newCreditLimit) {
        checkCreditLimit(newCreditLimit);
        creditLimit = newCreditLimit;
    }

    /**
     * Changes credit fee to a new value
     * @param newCreditFee New value of a credit fee
     */
    public void changeCreditFee(BigDecimal newCreditFee)
    {
        checkCreditFee(newCreditFee);
        creditFee = newCreditFee;
    }

    /**
     * Checks if BigDecimal is suitable to be a credit limit
     * @param creditLimit BigDecimal to be checked
     * @throws BanksException Throws exception if the given BigDecimal is invalid
     */
    private void checkCreditLimit(BigDecimal creditLimit) throws BanksException {
        if (creditLimit.compareTo(BigDecimal.valueOf(0)) < 0)
        {
            throw BankConditionsException.creditLimitIsNegativeException();
        }
    }

    /**
     * Checks if BigDecimal is suitable to be a credit fee
     * @param creditFee BigDecimal to be checked
     * @throws BanksException Throws exception if the given BigDecimal is invalid
     */
    private void checkCreditFee(BigDecimal creditFee) throws BanksException {
        if (creditFee.compareTo(BigDecimal.valueOf(0)) < 0)
        {
            throw BankConditionsException.creditFeeIsNegativeException();
        }
    }
}
