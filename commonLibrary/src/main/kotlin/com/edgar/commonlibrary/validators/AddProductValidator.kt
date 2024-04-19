package com.edgar.commonlibrary.validators

import com.edgar.commonlibrary.annotations.ValidAddProduct
import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.commonlibrary.exceptions.InvalidProductDataException
import com.edgar.commonlibrary.util.ErrorMessages
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.math.BigDecimal

class AddProductValidator : ConstraintValidator<ValidAddProduct, AddProductDto> {
    override fun isValid(addProductDto: AddProductDto, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return validateName(addProductDto.name) &&
                validateDescription(addProductDto.description) &&
                validatePrice(addProductDto.price)
    }

    private fun validateName(name: String?): Boolean {
        if (name.isNullOrEmpty()) {
            throw InvalidProductDataException(ErrorMessages.INVALID_PRODUCT_NAME_MESSAGE)
        }

        return true
    }

    private fun validatePrice(price: BigDecimal?): Boolean {
        if (price == null) {
            throw InvalidProductDataException(ErrorMessages.NULL_PRODUCT_PRICE_MESSAGE)
        }

        if (price < BigDecimal.ZERO) {
            throw InvalidProductDataException(ErrorMessages.NEGATIVE_PRODUCT_PRICE_MESSAGE)
        }

        return true
    }

    private fun validateDescription(description: String?): Boolean {
        if (description.isNullOrEmpty()) {
            throw InvalidProductDataException(ErrorMessages.INVALID_PRODUCT_DESCRIPTION_MESSAGE)
        }

        return true
    }
}