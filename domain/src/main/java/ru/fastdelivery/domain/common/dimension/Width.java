package ru.fastdelivery.domain.common.dimension;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Width extends Demension implements Comparable<Width> {

    private final BigInteger width;

    public Width(BigInteger width) {
        super(width);
        this.width = width;
    }

    @Override
    public int compareTo(Width o) {
        return o.getWidth().compareTo(width);
    }
}
