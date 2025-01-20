package ru.fastdelivery.presentation.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LocationCoordinate {

    @NotNull
    private final BigDecimal latitude;

    @NotNull
    private final BigDecimal longitude;

    public LocationCoordinate(double latitude, double longitude) {
        this.latitude = BigDecimal.valueOf(latitude);
        this.longitude = BigDecimal.valueOf(longitude);
    }

}
