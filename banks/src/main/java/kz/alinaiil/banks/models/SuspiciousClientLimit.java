package kz.alinaiil.banks.models;

import kz.alinaiil.banks.exceptions.BankConditionsException;
import kz.alinaiil.banks.exceptions.BanksException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * Checks and stores limit set for suspicious clients
 * @author alinaiil
 * @version 1.0
 */
@Getter
@Setter
public class SuspiciousClientLimit {
    /**
     * Checks if BigDecimal is suitable to be a limit for suspicious clients
     * @param suspiciousClientLimit Limit to check and store
     */
    public SuspiciousClientLimit(BigDecimal suspiciousClientLimit) {
        checkLimit(suspiciousClientLimit);
        limit = suspiciousClientLimit;
    }

    /**
     * Stores the limit
     */
    public BigDecimal limit;

    /**
     * Changes limit to a new value
     * @param newLimit New value of the limit
     */
    public void changeLimit(BigDecimal newLimit) {
        limit = newLimit;
    }

    /**
     * Checks if BigDecimal is suitable to be a limit
     * @param suspiciousClientLimit BigDecimal to be checked
     * @throws BanksException Throws exception if the given BigDecimal is negative
     */
    private void checkLimit(BigDecimal suspiciousClientLimit) throws BanksException {
        if (suspiciousClientLimit.compareTo(BigDecimal.valueOf(0)) < 0)
        {
            throw BankConditionsException.suspiciousClientLimitIsNegativeException();
        }
    }
}
