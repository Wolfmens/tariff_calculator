package ru.fastdelivery.domain.common.dimension;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Height extends Demension implements Comparable<Height> {

    private final BigInteger height;

    public Height(BigInteger height) {
        super(height);
        this.height = height;
    }

    @Override
    public int compareTo(Height o) {
        return o.getHeight().compareTo(height);
    }
}
