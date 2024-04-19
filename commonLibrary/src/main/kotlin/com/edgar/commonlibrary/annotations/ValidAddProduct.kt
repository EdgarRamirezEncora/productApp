package com.edgar.commonlibrary.annotations

import com.edgar.commonlibrary.validators.AddProductValidator
import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [AddProductValidator::class])
annotation class ValidAddProduct(
    val message: String = "Error: Invalid AddProductDTO",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)