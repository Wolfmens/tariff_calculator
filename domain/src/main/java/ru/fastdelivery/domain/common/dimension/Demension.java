package ru.fastdelivery.domain.common.dimension;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public abstract class Demension {

    protected Demension(BigInteger demensionParam) {
        if (isLessThanZero(demensionParam)) {
            log.error("Demension {} param less than zero", demensionParam);
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " cannot be below Zero!");
        }
        if (isOverThenFifteenHundred(demensionParam)) {
            log.error("Demension {} param is over than Fifteen Hundred", demensionParam);
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
