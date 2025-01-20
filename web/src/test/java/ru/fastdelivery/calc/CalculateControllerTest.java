package ru.fastdelivery.calc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.domain.common.coordinate.CoordinateDeliveryFactory;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.DepartureDelivery;
import ru.fastdelivery.presentation.api.request.DestinationDelivery;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CalculateControllerTest extends ControllerTest {

    final String baseCalculateApi = "/api/v1/calculate/";
    @MockBean
    TariffCalculateUseCase useCase;
    @MockBean
    CurrencyFactory currencyFactory;
    @MockBean
    CoordinateDeliveryFactory coordinateDeliveryFactory;

    @Test
    @DisplayName("Валидные данные для расчета стоимость -> Ответ 200")
    void whenValidInputData_thenReturn200() {
        var requestBody = getCalculatePackagesRequest();

        var rub = new CurrencyFactory(code -> true).create("RUB");
        when(useCase.calc(any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));

        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi, requestBody, CalculatePackagesResponse.class);

        var actualResponseBody = response.getBody();
        var expectedResponseBody = new CalculatePackagesResponse(
                new Price(BigDecimal.valueOf(10),rub),
                new Price(BigDecimal.valueOf(5),rub));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    @DisplayName("Список упаковок == null -> Ответ 400")
    void whenEmptyListPackages_thenReturn400() {
        var request = new CalculatePackagesRequest(null, "RUB", null, null);

        ResponseEntity<String> response = restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    private static CalculatePackagesRequest getCalculatePackagesRequest() {
        var destination = new DestinationDelivery(45, 60);
        var departure = new DepartureDelivery(50, 45);
        var cargoPackage = new CargoPackage(
                BigInteger.TEN,
                BigInteger.TWO,
                BigInteger.ONE,
                BigInteger.TEN);

        return new CalculatePackagesRequest(
                List.of(cargoPackage),
                "RUB",
                destination,
                departure);
    }
}
