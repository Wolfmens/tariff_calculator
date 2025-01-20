package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.coordinate.CoordinateDelivery;
import ru.fastdelivery.domain.common.coordinate.CoordinateDeliveryFactory;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@Slf4j
@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {

    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;
    private final CoordinateDeliveryFactory coordinateDeliveryFactory;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {
        log.info("Request for calc cost delivery: {} ", request);
        var packs = request.packages().stream()
                .map(cargoPackage -> new Pack(
                        new Weight(cargoPackage.weight()),
                        new Length(cargoPackage.length()),
                        new Width(cargoPackage.width()),
                        new Height(cargoPackage.height())
                ))
                .toList();

        Currency currencyFromRequest = currencyFactory.create(request.currencyCode());
        CoordinateDelivery coordinateDelivery =
                coordinateDeliveryFactory.createCoordinateDelivery(
                        request.destination().getLatitude(), request.destination().getLongitude(),
                        request.departure().getLatitude(), request.departure().getLongitude()
                );

        var updatesPacks = tariffCalculateUseCase.roundingUpPacksDemensions(packs);

        var shipment = new Shipment(updatesPacks, currencyFromRequest, coordinateDelivery);

        var calculatedPrice = tariffCalculateUseCase.calc(shipment);
        var minimalPrice = tariffCalculateUseCase.minimalPrice();

        CalculatePackagesResponse calculatePackagesResponse = new CalculatePackagesResponse(calculatedPrice, minimalPrice);

        log.info("Response for calc cost delivery: {} ", calculatePackagesResponse);

        return calculatePackagesResponse;
    }
}

