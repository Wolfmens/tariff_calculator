package ru.fastdelivery.domain.common.coordinate;


import java.util.Objects;

public record CoordinateDelivery(Destination destination, Departure departure) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinateDelivery that = (CoordinateDelivery) o;
        return Objects.equals(departure, that.departure) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, departure);
    }
}
