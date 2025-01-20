package ru.fastdelivery.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.coordinate.CoordinateDeliveryFactory;
import ru.fastdelivery.domain.common.coordinate.CoordinatePropertiesProvider;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.properties.provider.AvailableCoordinateDeliveryProperty;
import ru.fastdelivery.usecase.DistanceStepPropertyProvider;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public CoordinateDeliveryFactory coordinateDeliveryFactory(CoordinatePropertiesProvider coordinatePropertiesProvider) {
        return new CoordinateDeliveryFactory(coordinatePropertiesProvider);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(
            WeightPriceProvider weightPriceProvider,
            VolumePriceProvider volumePriceProvider,
            DistanceStepPropertyProvider distanceStepPropertyProvider) {

        return new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider, distanceStepPropertyProvider);
    }
}
