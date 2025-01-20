package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.fastdelivery.domain.common.coordinate.enam.CoordinateParam;
import ru.fastdelivery.properties.provider.AvailableCoordinateDeliveryProperty;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AvailableCoordinateDeliveryPropertyTest {

    public static final Long LATITUDE_MAX = 60L;
    public static final Long LATITUDE_MIN = 45L;
    public static final Long LONGITUDE_MAX = 90L;
    public static final Long LONGITUDE_MIN = 40L;

    AvailableCoordinateDeliveryProperty availableCoordinateDeliveryProperty;

    @BeforeEach
    void init() {
        availableCoordinateDeliveryProperty = new AvailableCoordinateDeliveryProperty();

        availableCoordinateDeliveryProperty.setLatitudeMax(LATITUDE_MAX);
        availableCoordinateDeliveryProperty.setLatitudeMin(LATITUDE_MIN);
        availableCoordinateDeliveryProperty.setLongitudeMax(LONGITUDE_MAX);
        availableCoordinateDeliveryProperty.setLongitudeMin(LONGITUDE_MIN);
    }


    @ParameterizedTest
    @ValueSource(longs = {45, 46, 55, 59})
    void whenRightLatitudeParams_whenReturnFalse(long latitude) {
        BigDecimal coordinateLatitudeProperty = BigDecimal.valueOf(latitude);

        boolean isValidLatitudeActualValue = availableCoordinateDeliveryProperty
                .isNotValidateCoordinates(List.of(coordinateLatitudeProperty), CoordinateParam.LATITUDE);

        assertFalse(isValidLatitudeActualValue);
    }


    @ParameterizedTest
    @ValueSource(longs = {18, 37, 61, 75})
    void whenNotRightLatitudeParams_whenReturnTrue(long latitude) {
        BigDecimal coordinateLatitudeProperty = BigDecimal.valueOf(latitude);

        boolean isNotValidLatitudeActualValue = availableCoordinateDeliveryProperty
                .isNotValidateCoordinates(List.of(coordinateLatitudeProperty), CoordinateParam.LATITUDE);

        assertTrue(isNotValidLatitudeActualValue);
    }

    @ParameterizedTest
    @ValueSource(longs = {42, 57, 70, 80})
    void whenRightLongitudeParams_whenReturnFalse(long longitude) {
        BigDecimal coordinateLongitudeProperty = BigDecimal.valueOf(longitude);

        boolean isValidLatitudeActualValue = availableCoordinateDeliveryProperty
                .isNotValidateCoordinates(List.of(coordinateLongitudeProperty), CoordinateParam.LONGITUDE);

        assertFalse(isValidLatitudeActualValue);
    }

    @ParameterizedTest
    @ValueSource(longs = {35, 39, 91, 105})
    void whenNotRightLongitudeParams_whenReturnTrue(long longitude) {
        BigDecimal coordinateLatitudeProperty = BigDecimal.valueOf(longitude);

        boolean isNotValidLatitudeActualValue = availableCoordinateDeliveryProperty
                .isNotValidateCoordinates(List.of(coordinateLatitudeProperty), CoordinateParam.LONGITUDE);

        assertTrue(isNotValidLatitudeActualValue);
    }

}
