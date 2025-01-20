package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(
        @Schema(description = "Список упаковок отправления",
                example = "[{" +
                        "\"weight\": 4056.45, " +
                        "\"length\": 345," +
                        "\"width\": 589," +
                        "\"height\": 234" +
                        "}]")
        @NotNull
        @NotEmpty
        @Valid
        List<CargoPackage> packages,

        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull
        String currencyCode,

        @Schema(description = "Координаты прибытия", example = """
                {
                     "latitude" : 73.398660,
                     "longitude" : 55.027532
                }
                """)
        @NotNull
        @Valid
        DestinationDelivery destination,

        @Schema(description = "Координаты прибытия", example = """
                {
                     "latitude" : 55.446008,
                     "longitude" : 65.339151
                }
                """)
        @NotNull
        @Valid
        DepartureDelivery departure
) {
}
