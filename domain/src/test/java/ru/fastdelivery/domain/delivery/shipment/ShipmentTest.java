package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    Weight weight1;
    Length lenght1;
    Height height1;
    Width width1;

    Weight weight2;
    Length lenght2;
    Height height2;
    Width width2;

    List<Pack> packages;

    Shipment shipment;

    @BeforeEach
    void init() {
        weight1 = new Weight(BigInteger.valueOf(500));
        lenght1 = new Length(BigInteger.valueOf(500));
        height1 = new Height(BigInteger.valueOf(400));
        width1 = new Width(BigInteger.valueOf(300));

        weight2 = new Weight(BigInteger.valueOf(1200));
        lenght2 = new Length(BigInteger.valueOf(545));
        height2 = new Height(BigInteger.valueOf(675));
        width2 = new Width(BigInteger.valueOf(521));

        packages = List.of(
                new Pack(weight1, lenght1, width1, height1),
                new Pack(weight2, lenght2, width2, height2));

        shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));
    }

    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(1700));
    }

    @Test
    void whenSummarizingVolumesOfAllPackages_thenReturnSum() {
        var volumeOfShipment = shipment.volumeAllPackages();

        assertThat(volumeOfShipment.getVolume()).isEqualTo(BigInteger.valueOf(251_662_875));
    }
}