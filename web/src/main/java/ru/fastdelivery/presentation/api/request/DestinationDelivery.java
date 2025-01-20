package ru.fastdelivery.presentation.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DestinationDelivery extends LocationCoordinate {

    @JsonCreator
    public DestinationDelivery(@JsonProperty("latitude") double latitude,
                               @JsonProperty("longitude") double longitude) {
        super(latitude, longitude);
    }
}
