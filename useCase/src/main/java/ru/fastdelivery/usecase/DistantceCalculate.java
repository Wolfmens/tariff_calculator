package ru.fastdelivery.usecase;

import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;

import java.math.BigDecimal;

@Slf4j
public class DistantceCalculate {

    private DistantceCalculate() {
    }

    public static BigDecimal calculateDistanceBy(Destination destination, Departure departure) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point destinationPoint = geometryFactory.createPoint(new Coordinate(
                destination.latitude().doubleValue(),
                destination.longitude().doubleValue()));

        Point departurePoint = geometryFactory.createPoint(new Coordinate(
                departure.latitude().doubleValue(),
                departure.longitude().doubleValue()));

        log.info("Distance from {} to {} -> result: {}", destinationPoint, departurePoint, departurePoint.distance(destinationPoint));

        return BigDecimal.valueOf(departurePoint.distance(destinationPoint));
    }

}
