package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {

    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;
    private final DistanceStepPropertyProvider distanceStepPropertyProvider;

    public List<Pack> roundingUpPacksDemensions(List<Pack> packages) {
        List<Pack> updatesPackages = new ArrayList<>();

        for (Pack pack : packages) {
            Height newHeight = new Height(roundingParam(pack.height().getHeight()));
            Width newWidth = new Width(roundingParam(pack.width().getWidth()));
            Length newLength = new Length(roundingParam(pack.length().getLength()));
            updatesPackages.add(new Pack(pack.weight(), newLength, newWidth, newHeight));
        }

        return updatesPackages;
    }

    public BigInteger roundingParam(BigInteger startParam) {
        while (startParam.intValue() % 50 != 0) {
            startParam = startParam.add(BigInteger.ONE);
        }

        return startParam;
    }

    public Price calc(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var volumeAllPackagesMetr = shipment.volumeAllPackages().mmToMeterCube();

        Price baseTotalPriceByWeightAndVolumeShipment = getBaseTotalPrice(volumeAllPackagesMetr, weightAllPackagesKg);

        BigDecimal distanceDeliveryByCoordinate = DistantceCalculate.calculateDistanceBy(
                shipment.coordinateDelivery().destination(),
                shipment.coordinateDelivery().departure());

        BigInteger minStepDistanceByBaseTotalPrice = distanceStepPropertyProvider.getMinStepDistance();

        return getTotalPriceShipmentBy
                (baseTotalPriceByWeightAndVolumeShipment, distanceDeliveryByCoordinate, minStepDistanceByBaseTotalPrice);
    }

    public Price getTotalPriceShipmentBy(Price baseTotalPriceByWeightAndVolumeShipment,
                                         BigDecimal distanceDeliveryByCoordinate,
                                         BigInteger minStepDistanceByBaseTotalPrice) {

        if (distanceDeliveryByCoordinate.compareTo(new BigDecimal(minStepDistanceByBaseTotalPrice)) <= 0) {
            return baseTotalPriceByWeightAndVolumeShipment;
        }

        BigDecimal valueHowManyIsOverConstantStep = distanceDeliveryByCoordinate.divide
                (new BigDecimal(minStepDistanceByBaseTotalPrice), 2, RoundingMode.HALF_UP);

        BigDecimal totalFinallyCostDelivery = valueHowManyIsOverConstantStep.multiply(
                baseTotalPriceByWeightAndVolumeShipment.amount());


        return new Price(totalFinallyCostDelivery, baseTotalPriceByWeightAndVolumeShipment.currency());
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }


    public Price getBaseTotalPrice(BigDecimal volumeAllPackagesMetr,
                                   BigDecimal weightAllPackagesKg) {
        Price totalPriceByVolume = volumePriceProvider
                .costPerCubMetr()
                .multiply(volumeAllPackagesMetr);

        Price totalPriceByWeight = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg);

        var minimalPrice = weightPriceProvider.minimalPrice();

        return totalPriceByWeight
                .max(totalPriceByVolume)
                .max(minimalPrice);
    }
}
