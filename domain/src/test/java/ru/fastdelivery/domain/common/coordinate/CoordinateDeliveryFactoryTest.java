package ru.fastdelivery.domain.common.coordinate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coordinate.enam.CoordinateParam;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class CoordinateDeliveryFactoryTest {

    CoordinatePropertiesProvider mockCoordinatePropertiesProvider;
    CoordinateDeliveryFactory coordinateDeliveryFactory;

    BigDecimal destinationLatitude;
    BigDecimal destinationLongitude;
    BigDecimal departureLatitude;
    BigDecimal departureLongitude;

    @BeforeEach
    void init() {
        mockCoordinatePropertiesProvider = mock(CoordinatePropertiesProvider.class);
        coordinateDeliveryFactory = new CoordinateDeliveryFactory(mockCoordinatePropertiesProvider);

        destinationLatitude = new BigDecimal("46");
        destinationLongitude = new BigDecimal("55");
        departureLatitude = new BigDecimal("59");
        departureLongitude = new BigDecimal("60");
    }

    @Test
    void whenLatitudeParamsNonValid_thenThrowIllegalArgumentException() {
        List<BigDecimal> coordinates = List.of(destinationLatitude, departureLatitude);

        doReturn(true)
                .when(mockCoordinatePropertiesProvider)
                .isNotValidateCoordinates(coordinates, CoordinateParam.LATITUDE);

        assertThrows(IllegalArgumentException.class, () ->
                coordinateDeliveryFactory.createCoordinateDelivery
                        (destinationLatitude, destinationLongitude, departureLatitude, departureLongitude));
    }

    @Test
    void whenLongitudeParamsNonValid_thenThrowIllegalArgumentException() {
        List<BigDecimal> coordinates = List.of(destinationLongitude, departureLongitude);

        doReturn(false)
                .when(mockCoordinatePropertiesProvider)
                .isNotValidateCoordinates(coordinates, CoordinateParam.LATITUDE);

        doReturn(true)
                .when(mockCoordinatePropertiesProvider)
                .isNotValidateCoordinates(coordinates, CoordinateParam.LONGITUDE);

        assertThrows(IllegalArgumentException.class, () ->
                coordinateDeliveryFactory.createCoordinateDelivery
                        (destinationLatitude, destinationLongitude, departureLatitude, departureLongitude));
    }

    @Test
    void whenCoordinateParamIsValid_thenReturnCreatedObject() {
        doReturn(false)
                .when(mockCoordinatePropertiesProvider)
                .isNotValidateCoordinates(List.of(departureLatitude, destinationLatitude), CoordinateParam.LATITUDE);

        doReturn(false)
                .when(mockCoordinatePropertiesProvider)
                .isNotValidateCoordinates(List.of(destinationLongitude, departureLongitude), CoordinateParam.LONGITUDE);

        CoordinateDelivery expectedCoordinateDelivery = new CoordinateDelivery(
                new Destination(destinationLatitude, destinationLongitude),
                new Departure(departureLatitude, departureLongitude));

        CoordinateDelivery actualCoordinateDelivery = coordinateDeliveryFactory.createCoordinateDelivery(
                destinationLatitude,
                destinationLongitude,
                departureLatitude,
                departureLongitude);

        assertNotNull(expectedCoordinateDelivery);
        assertNotNull(actualCoordinateDelivery);

        assertEquals(expectedCoordinateDelivery, actualCoordinateDelivery);
    }

}