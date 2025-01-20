package ru.fastdelivery.domain.common.coordinate;

import ru.fastdelivery.domain.common.coordinate.enam.CoordinateParam;

import java.math.BigDecimal;
import java.util.List;

public interface CoordinatePropertiesProvider {

    boolean isNotValidateCoordinates(List<BigDecimal> valuesFromRequest, CoordinateParam param);
}
