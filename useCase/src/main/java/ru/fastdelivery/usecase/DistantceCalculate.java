package ru.fastdelivery.usecase;

import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class DistantceCalculate {

    private static final Double COEFF_KM_PER_DEGREE = 111.32;

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

        double distanceDegrees = departurePoint.distance(destinationPoint);
        double distanceMeters = distanceDegrees * COEFF_KM_PER_DEGREE;

        return BigDecimal.valueOf(distanceMeters).setScale(2, RoundingMode.HALF_UP);
    }

}
