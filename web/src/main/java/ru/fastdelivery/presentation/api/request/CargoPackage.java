package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Range;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,
        @Schema(description = "Длина упаковки, мм", example = "345")
        BigInteger length,
        @Schema(description = "Ширина упаковки, мм", example = "589")
        BigInteger width,
        @Schema(description = "Высота упаковки, мм", example = "234")
        BigInteger height
) {
}
