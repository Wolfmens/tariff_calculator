package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.coordinate.CoordinateDelivery;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.pack.VolumePack;

import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency,
        CoordinateDelivery coordinateDelivery
) {

    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }

    public VolumePack volumeAllPackages() {
        return packages.stream()
                .map(pack -> VolumePack.getVolumePack(
                        pack.height().getHeight(),
                        pack.length().getLength(),
                        pack.width().getWidth()))
                .reduce(VolumePack.zero(), VolumePack::addVolume);
    }
}
