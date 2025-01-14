package ru.fastdelivery.domain.delivery.pack;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

@AllArgsConstructor
@Getter
public class VolumePack {

    private BigInteger volume;

    public static VolumePack getVolumePack(BigInteger height, BigInteger lenght, BigInteger width) {
       return new VolumePack(height.multiply(lenght).multiply(width));
    }

    public static VolumePack zero() {
        return new VolumePack(BigInteger.ZERO);
    }

    public VolumePack addVolume(VolumePack volumePack) {
        return new VolumePack(this.volume.add(volumePack.volume));
    }

    public BigDecimal mmToMeterCube() {
        MathContext context = new MathContext(4, RoundingMode.HALF_UP);

        return new BigDecimal(volume)
                .divide(BigDecimal.valueOf(1_000_000_000),4,  RoundingMode.HALF_UP);
    }
}
