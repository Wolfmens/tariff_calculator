package ru.fastdelivery.domain.common.dimension;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Length extends Demension implements Comparable<Length> {

    private final BigInteger length;

    public Length(BigInteger length) {
        super(length);
        this.length = length;
    }

    @Override
    public int compareTo(Length o) {
        return o.getLength().compareTo(length);
    }
}
