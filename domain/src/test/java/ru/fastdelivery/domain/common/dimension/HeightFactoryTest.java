package ru.fastdelivery.domain.common.dimension;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HeightFactoryTest {

    @ParameterizedTest(name = "Значение высоты = {arguments} -> объект создан")
    @ValueSource(longs = {200, 300, 1, 1_000})
    void thenLengthGreaterThenZeroAndLessThenFifteenHundred_thenReturnCreatedObj(long value) {
        var height = new Height(BigInteger.valueOf(value));

        assertNotNull(height);
        assertThat(height.getHeight()).isEqualByComparingTo(BigInteger.valueOf(value));
    }

    @ParameterizedTest(name = "Значение высоты меньше 0 = {arguments} -> выбрасывается исключение")
    @ValueSource(longs = {-200, -300, -1, -1_000})
    void thenLengthLessThenZero_thenThrowException(long value) {
        assertThatThrownBy(() -> new Height(BigInteger.valueOf(value)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "Значение высоты больше 1500 = {arguments} -> выбрасывается исключение")
    @ValueSource(longs = {1_600, 2_000, 1_501, 1_999})
    void thenLengthGreatherThenFifteenHundred_thenThrowException(long value) {
        assertThatThrownBy(() -> new Height(BigInteger.valueOf(value)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
