package ru.fastdelivery.domain.delivery.pack;

import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
@Slf4j
public record Pack(Weight weight, Length length, Width width, Height height) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));

    public Pack {
        if (weight.greaterThan(maxWeight)) {
            log.error("Pack weight {} greater than maxWeight", weight);
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        }
    }
}
