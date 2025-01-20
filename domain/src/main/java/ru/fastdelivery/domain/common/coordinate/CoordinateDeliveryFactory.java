package ru.fastdelivery.domain.common.coordinate;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.coordinate.enam.CoordinateParam;

import java.math.BigDecimal;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CoordinateDeliveryFactory {

    private final CoordinatePropertiesProvider coordinatePropertiesProvider;

    public CoordinateDelivery createCoordinateDelivery(BigDecimal destinationLatitude,
                                                       BigDecimal destinationLongitude,
                                                       BigDecimal departureLatitude,
                                                       BigDecimal departureLongitude) {

        if (coordinatePropertiesProvider.isNotValidateCoordinates(List.of(destinationLatitude, departureLatitude), CoordinateParam.LATITUDE)) {
            throw new IllegalArgumentException("Latitude is less 45 or over 65");
        }

        if (coordinatePropertiesProvider.isNotValidateCoordinates(List.of(destinationLongitude, departureLongitude), CoordinateParam.LONGITUDE)) {
            throw new IllegalArgumentException("Longitude is less 30 or over 96");
        }

        return new CoordinateDelivery(
                new Destination(destinationLatitude, destinationLongitude),
                new Departure(departureLatitude, departureLongitude));
    }
}
