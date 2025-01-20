package ru.fastdelivery.properties.provider;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.coordinate.CoordinatePropertiesProvider;
import ru.fastdelivery.domain.common.coordinate.enam.CoordinateParam;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "coordinate")
@Setter
@Getter
@Slf4j
public class AvailableCoordinateDeliveryProperty implements CoordinatePropertiesProvider {

    private Long latitudeMax;

    private Long latitudeMin;

    private Long longitudeMax;

    private Long longitudeMin;

    @Override
    public boolean isNotValidateCoordinates(List<BigDecimal> valuesFromRequest, CoordinateParam param) {

        return param.equals(CoordinateParam.LATITUDE) ?
                isNotValid(valuesFromRequest, latitudeMax, latitudeMin) :
                isNotValid(valuesFromRequest, longitudeMax, longitudeMin);
    }

    private boolean isNotValid(List<BigDecimal> valuesFromRequest, Long maxCoordinate, Long minCoordinate) {
        for (BigDecimal value : valuesFromRequest) {
            if (!(minCoordinate <= value.longValue() && value.longValue() <= maxCoordinate)) {
                log.error("Координата: {} не валидна и выходит за область ограничений: {} - {}",
                        value.longValue(), maxCoordinate, minCoordinate);
                return true;
            }
            log.info("Координаты в запросе верны");
        }
        return false;
    }
}
