package com.edgar.commonlibrary.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.math.BigDecimal

@Data
@NoArgsConstructor
@AllArgsConstructor
class ProductDto (
    var id :Long,
    @NotBlank(message = "The name is mandatory.")
    @NotNull(message = "The name must not be null.")
    var name : String,
    @NotBlank(message = "The description is mandatory.")
    @NotNull(message = "The description must not be null.")
    var description : String,
    @Min(value = 0, message = "The price must be greater than zero.")
    @NotNull(message = "The price must not be null.")
    var price : BigDecimal
)