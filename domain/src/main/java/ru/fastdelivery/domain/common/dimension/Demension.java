package ru.fastdelivery.domain.common.dimension;

import java.math.BigInteger;

public abstract class Demension {

    protected Demension(BigInteger demensionParam) {
        if (isLessThanZero(demensionParam)) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " cannot be below Zero!");
        }
        if (isOverThenFifteenHundred(demensionParam)) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " cannot be over Fifteen Hundred!");
        }
    }

    private boolean isOverThenFifteenHundred(BigInteger demensionParam) {
        return demensionParam.compareTo(BigInteger.valueOf(1500L)) > 0;
    }

    private static boolean isLessThanZero(BigInteger param) {
        return BigInteger.ZERO.compareTo(param) > 0;
    }



}
