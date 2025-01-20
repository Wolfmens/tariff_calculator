package ru.fastdelivery.usecase;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DistantceCalculateTest {

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of(
                        new Destination(BigDecimal.valueOf(45), BigDecimal.valueOf(60)),
                        new Departure(BigDecimal.valueOf(50), BigDecimal.valueOf(45)),
                        BigDecimal.valueOf(1760.12)
                ),
                Arguments.of(
                        new Destination(BigDecimal.valueOf(55), BigDecimal.valueOf(63)),
                        new Departure(BigDecimal.valueOf(49), BigDecimal.valueOf(52)),
                        BigDecimal.valueOf(1394.84)
        ));
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void whenCalcDistanceBetweenCorinate_thenReturnDistance(Destination destination, Departure departure, BigDecimal result) {
        var destinationKmBetweenCordinates = DistantceCalculate.calculateDistanceBy(destination, departure);

        assertNotNull(destinationKmBetweenCordinates);
        assertThat(destinationKmBetweenCordinates).isEqualByComparingTo(result);
    }

}
