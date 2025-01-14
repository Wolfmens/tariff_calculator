package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        var lenght = new Length(BigInteger.valueOf(500));
        var height = new Height(BigInteger.valueOf(400));
        var width = new Width(BigInteger.valueOf(300));

        assertThatThrownBy(() -> new Pack(weight, lenght, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenLengthMoreThanFifteenHundred_thenThrowException() {
        assertThatThrownBy(() -> new Pack(
                new Weight(BigInteger.valueOf(1_000)),
                new Length(BigInteger.valueOf(1700)),
                new Width(BigInteger.valueOf(300)),
                new Height(BigInteger.valueOf(450))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenHeightMoreThanFifteenHundred_thenThrowException() {
        assertThatThrownBy(() -> new Pack(
                new Weight(BigInteger.valueOf(1_000)),
                new Length(BigInteger.valueOf(350)),
                new Width(BigInteger.valueOf(300)),
                new Height(BigInteger.valueOf(1700))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenWidthMoreThanFifteenHundred_thenThrowException() {
        assertThatThrownBy(() -> new Pack(
                new Weight(BigInteger.valueOf(1_000)),
                new Length(BigInteger.valueOf(350)),
                new Width(BigInteger.valueOf(1700)),
                new Height(BigInteger.valueOf(450))))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void whenWeightLessThanMaxWeightAndValidateDemensions_thenObjectCreated() {
        var actual = new Pack(
                new Weight(BigInteger.valueOf(1_000)),
                new Length(BigInteger.valueOf(350)),
                new Width(BigInteger.valueOf(300)),
                new Height(BigInteger.valueOf(450))
        );

        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }
}