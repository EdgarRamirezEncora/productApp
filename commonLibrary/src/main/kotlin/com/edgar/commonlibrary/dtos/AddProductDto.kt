package com.edgar.commonlibrary.dtos

import com.edgar.commonlibrary.annotations.ValidAddProduct
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.math.BigDecimal

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidAddProduct
class AddProductDto (
    var name : String,
    var description : String,
    var price : BigDecimal
)