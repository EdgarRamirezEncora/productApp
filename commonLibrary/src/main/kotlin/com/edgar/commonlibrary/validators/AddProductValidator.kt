package com.edgar.commonlibrary.validators

import com.edgar.commonlibrary.annotations.ValidAddProduct
import com.edgar.commonlibrary.dtos.AddProductDto
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.math.BigDecimal

class AddProductValidator : ConstraintValidator<ValidAddProduct, AddProductDto> {
    override fun isValid(addProductDto: AddProductDto?, constraintValidatorContext: ConstraintValidatorContext?): Boolean {
        return validateName(addProductDto?.name ?: "") &&
                validateDescription(addProductDto?.description ?: "") &&
                validatePrice(addProductDto?.price ?: BigDecimal("-1.0"))
    }

    private fun validateName(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun validatePrice(price: BigDecimal): Boolean {
        return price >= BigDecimal.ZERO
    }

    private fun validateDescription(description: String): Boolean {
        return description.isNotBlank()
    }
}