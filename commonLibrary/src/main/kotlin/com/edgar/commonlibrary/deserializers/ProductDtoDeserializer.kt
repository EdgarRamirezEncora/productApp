package com.edgar.commonlibrary.deserializers

import com.edgar.commonlibrary.dtos.ProductDto
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class ProductDtoDeserializer {
    companion object {
        private val mapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    }

    fun deserializeProductDto(response: Any?): ProductDto {
        return mapper.convertValue(response, ProductDto::class.java)
    }

    fun deserializeProductList(response: Any?): List<ProductDto> {
        return mapper.convertValue(response,
            object : TypeReference<List<ProductDto>>() {})
    }
}