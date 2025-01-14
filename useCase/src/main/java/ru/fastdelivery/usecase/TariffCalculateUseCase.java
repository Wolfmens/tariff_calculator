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
import java.util.ArrayList;
import java.util.List;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;

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
        while(startParam.intValue() % 50 != 0) {
           startParam = startParam.add(BigInteger.ONE);
        }

        return startParam;
    }

    public Price calc(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var minimalPrice = weightPriceProvider.minimalPrice();
        var volumeAllPackagesMetr = shipment.volumeAllPackages().mmToMeterCube();

        Price totalPriceByVolume = volumePriceProvider
                .costPerCubMetr()
                .multiply(volumeAllPackagesMetr);

        Price totalPriceByWeight = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg);

        return totalPriceByWeight
                .max(totalPriceByVolume)
                .max(minimalPrice);
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
