package ru.fastdelivery.presentation.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartureDelivery extends LocationCoordinate {

    @JsonCreator
    public DepartureDelivery(@JsonProperty("latitude") double latitude,
                             @JsonProperty("longitude") double longitude) {
        super(latitude, longitude);
    }
}
