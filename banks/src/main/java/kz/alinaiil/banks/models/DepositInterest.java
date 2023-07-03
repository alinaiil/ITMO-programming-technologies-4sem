package kz.alinaiil.banks.models;
import kz.alinaiil.banks.exceptions.BankConditionsException;
import kz.alinaiil.banks.exceptions.BanksException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;

/**
 * Checks and stores deposit interest
 * @author alinaiil
 * @version 1.0
 */
@Getter
public class DepositInterest {
    /**
     * Checks values and stores them if they are valid
     * @param lowerBound Value for a lower bound
     * @param upperBound Value for an upper bound
     * @param interest Value for an interest
     */
    public DepositInterest(BigDecimal lowerBound, BigDecimal upperBound, BigDecimal interest) {
        checkLowerBound(lowerBound, upperBound);
        checkUpperBound(upperBound, lowerBound);
        checkInterest(interest);
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.interest = interest;
    }

    /**
     * Lower bound of a range in which balance should fall to get this particular interest
     */
    public BigDecimal upperBound;
    /**
     * Upper bound of a range in which balance should fall to get this particular interest. Can be null if there's no upper bound
     */
    public BigDecimal lowerBound;
    /**
     * Interest for a given balance range
     */
    public BigDecimal interest;

    /**
     * Checks if opening balance falls in this particular range
     * @param openingBalance Opening balance to check
     * @return true - if it does fall, false - if it doesn't
     */
    public boolean isInRange(BigDecimal openingBalance) {
        if (upperBound == null) {
            return openingBalance.compareTo(lowerBound) >= 0;
        }

        return openingBalance.compareTo(lowerBound) >= 0 && openingBalance.compareTo(upperBound) < 0;
    }

    /**
     * Checks if the upper bound is valid and correct
     * @param upperBound Upper bound to check
     * @param lowerBound Lower bound to use in checks
     * @throws BanksException Throws exceptions if upper bound is invalid
     */
    private void checkUpperBound(BigDecimal upperBound, BigDecimal lowerBound) throws BanksException {
        if (upperBound != null && upperBound.compareTo(lowerBound) < 0) {
            throw BankConditionsException.depositInterestSwitchedBoundsException();
        }

        if (upperBound != null && upperBound.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw BankConditionsException.depositInterestNegativeBoundsException();
        }
    }

    /**
     * Checks if the upper bound is valid and correct
     * @param lowerBound Lower bound to check
     * @param upperBound Upper bound to use in checks
     * @throws BanksException Throws exceptions if lower bound is invalid
     */
    private void checkLowerBound(BigDecimal lowerBound, BigDecimal upperBound) throws BanksException {
        if (upperBound != null && upperBound.compareTo(lowerBound) < 0) {
            throw BankConditionsException.depositInterestSwitchedBoundsException();
        }

        if (lowerBound.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw BankConditionsException.depositInterestNegativeBoundsException();
        }
    }

    /**
     * Checks if the interest is valid and correct
     * @param interest Interest to check
     * @throws BanksException Throws exceptions if interest is invalid
     */
    private void checkInterest(BigDecimal interest) throws BanksException {
        if (interest.compareTo(BigDecimal.valueOf(0)) < 0 || interest.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw BankConditionsException.depositInterestIncorrectInterestException();
        }
    }
}
