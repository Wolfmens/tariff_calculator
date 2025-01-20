package ru.fastdelivery.domain.common.coordinate;

import java.math.BigDecimal;
import java.util.Objects;

public record Departure(BigDecimal latitude, BigDecimal longitude) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departure departure = (Departure) o;
        return Objects.equals(latitude, departure.latitude) && Objects.equals(longitude, departure.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
