package com.edgar.commonlibrary.dtos

import com.edgar.commonlibrary.annotations.ValidAddProduct
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.math.BigDecimal

@NoArgsConstructor
@AllArgsConstructor
@ValidAddProduct
data class AddProductDto (
    var name : String,
    var description : String,
    var price : BigDecimal
)