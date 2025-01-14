package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VolumePackTest {

    @Test
    void thenValidateData_thenCreatedObj() {
        var expectedVolumePack = new VolumePack(BigInteger.valueOf(5_400));

        var actualVolumePack = VolumePack.getVolumePack(
                BigInteger.valueOf(15),
                BigInteger.valueOf(15),
                BigInteger.valueOf(24));

        assertNotNull(actualVolumePack);
        assertNotNull(expectedVolumePack);
        assertThat(expectedVolumePack.getVolume()).isEqualByComparingTo(actualVolumePack.getVolume());
    }

    @Test
    void thenAddVolume_thenGetUpdateVolume() {
        var oldVolumePack = new VolumePack(BigInteger.valueOf(5_400));

        var volumePackForAdded = VolumePack.getVolumePack(
                BigInteger.valueOf(24),
                BigInteger.valueOf(42),
                BigInteger.valueOf(24));

        VolumePack actualVolumePack = oldVolumePack.addVolume(volumePackForAdded);
        VolumePack expectedVolumePack = new VolumePack(BigInteger.valueOf(29_592));

        assertNotNull(oldVolumePack);
        assertNotNull(volumePackForAdded);
        assertNotNull(actualVolumePack);
        assertNotNull(expectedVolumePack);

        assertThat(expectedVolumePack.getVolume()).isEqualByComparingTo(actualVolumePack.getVolume());
    }

    @Test
    void thenConvertCubeMMTOCubeMetr_thenGetConvertedVolume() {
        var volumePackForConvert = new VolumePack(BigInteger.valueOf(52_500_000));

        BigDecimal actualConvertedVolume = volumePackForConvert.mmToMeterCube();
        BigDecimal expectedConvertVolume = BigDecimal.valueOf(0.0525);

        assertNotNull(volumePackForConvert);
        assertThat(expectedConvertVolume).isEqualByComparingTo(actualConvertedVolume);
    }
}
