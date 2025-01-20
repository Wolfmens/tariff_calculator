package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.usecase.DistanceStepPropertyProvider;

import java.math.BigInteger;

@Configuration
@ConfigurationProperties("distance")
@Setter
public class DistanceStepProperty implements DistanceStepPropertyProvider {

    private Long step;

    public BigInteger getMinStepDistance() {
        return BigInteger.valueOf(step);
    }
}
