package ru.fastdelivery.domain.common.coordinate;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.coordinate.enam.CoordinateParam;

import java.math.BigDecimal;
import java.util.List;

@Data
@RequiredArgsConstructor
@Slf4j
public class CoordinateDeliveryFactory {

    private final CoordinatePropertiesProvider coordinatePropertiesProvider;

    public CoordinateDelivery createCoordinateDelivery(BigDecimal destinationLatitude,
                                                       BigDecimal destinationLongitude,
                                                       BigDecimal departureLatitude,
                                                       BigDecimal departureLongitude) {

        if (coordinatePropertiesProvider.isNotValidateCoordinates(List.of(destinationLatitude, departureLatitude), CoordinateParam.LATITUDE)) {
            log.error("Latitude params: {} выходят за ограничения", List.of(destinationLatitude, departureLatitude));
            throw new IllegalArgumentException("Latitude is less 45 or over 65");
        }

        if (coordinatePropertiesProvider.isNotValidateCoordinates(List.of(destinationLongitude, departureLongitude), CoordinateParam.LONGITUDE)) {
            log.error("Longitude params: {} выходят за ограничения", List.of(destinationLatitude, departureLatitude));
            throw new IllegalArgumentException("Longitude is less 30 or over 96");
        }

        return new CoordinateDelivery(
                new Destination(destinationLatitude, destinationLongitude),
                new Departure(departureLatitude, departureLongitude));
    }
}
