package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.fastdelivery.domain.common.coordinate.CoordinateDelivery;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TariffCalculateUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
    final DistanceStepPropertyProvider distanceStepPropertyProvider = mock(DistanceStepPropertyProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    final TariffCalculateUseCase tariffCalculateUseCase =
            new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider, distanceStepPropertyProvider);

    @Test
    @DisplayName("Расчет стоимости доставки -> успешно")
    void whenCalculatePrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerCubeMetr = new Price(BigDecimal.valueOf(200), currency);
        var distanceStepForCalculateStep = new BigInteger("450");

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(volumePriceProvider.costPerCubMetr()).thenReturn(pricePerCubeMetr);
        when(distanceStepPropertyProvider.getMinStepDistance()).thenReturn(distanceStepForCalculateStep);

        CoordinateDelivery coordinateDelivery = new CoordinateDelivery
                (new Destination(BigDecimal.valueOf(45), BigDecimal.valueOf(65)),
                        new Departure(BigDecimal.valueOf(45), BigDecimal.valueOf(65)));

        var shipment = new Shipment(
                List.of(new Pack(
                        new Weight(BigInteger.valueOf(4564)),
                        new Length(BigInteger.valueOf(345)),
                        new Width(BigInteger.valueOf(589)),
                        new Height(BigInteger.valueOf(234)))),
                new CurrencyFactory(code -> true).create("RUB"),
                coordinateDelivery);

        var expectedPrice = new Price(BigDecimal.valueOf(456.4), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @ParameterizedTest
    @CsvSource({
            "234, 250",
            "589, 600",
            "4564, 4600" })
    @DisplayName("Округление числа до кратного 50-ти -> успешно")
    void whenRoundingValue_thenReturnRoundedNumber(BigInteger oldValue, BigInteger expected) {
        BigInteger bigInteger = tariffCalculateUseCase.roundingParam(oldValue);

        assertThat(bigInteger).isEqualByComparingTo(expected);
    }

    @Test
    @DisplayName("Округление габаритов упаковки до значений кратным 50-ти -> успешно")
    void whenRoundingUpDemensionsAllPack_thenReturnUpdatedPack() {
       var weight1 = new Weight(BigInteger.valueOf(1340));
       var lenght1 = new Length(BigInteger.valueOf(234));
       var height1 = new Height(BigInteger.valueOf(660));
       var width1 = new Width(BigInteger.valueOf(215));

       var weight2 = new Weight(BigInteger.valueOf(1200));
       var lenght2 = new Length(BigInteger.valueOf(445));
       var height2 = new Height(BigInteger.valueOf(675));
       var width2 = new Width(BigInteger.valueOf(315));

       List<Pack> oldPackList = List.of(
                new Pack(weight1, lenght1, width1, height1),
                new Pack(weight2, lenght2, width2, height2));

        List<Pack> actualPackListWithRoundedDemensions = tariffCalculateUseCase.roundingUpPacksDemensions(oldPackList);

        var newWeight1 = new Weight(BigInteger.valueOf(1340));
        var newLenght1 = new Length(BigInteger.valueOf(250));
        var newHeight1 = new Height(BigInteger.valueOf(700));
        var newWidth1 = new Width(BigInteger.valueOf(250));

        var newWeight2 = new Weight(BigInteger.valueOf(1200));
        var newLenght2 = new Length(BigInteger.valueOf(450));
        var newHeight2 = new Height(BigInteger.valueOf(700));
        var newWidth2 = new Width(BigInteger.valueOf(350));

        List<Pack> expectedPackList = List.of(
                new Pack(newWeight1, newLenght1, newWidth1, newHeight1),
                new Pack(newWeight2, newLenght2, newWidth2, newHeight2));

        assertThat(actualPackListWithRoundedDemensions.size()).isEqualByComparingTo(expectedPackList.size());
        assertThatList(actualPackListWithRoundedDemensions).isEqualTo(expectedPackList);
    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        BigDecimal minimalValue = BigDecimal.TEN;
        var minimalPrice = new Price(minimalValue, currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var actual = tariffCalculateUseCase.minimalPrice();

        assertThat(actual).isEqualTo(minimalPrice);
    }
}