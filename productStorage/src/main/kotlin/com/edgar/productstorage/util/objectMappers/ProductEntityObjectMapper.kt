package com.edgar.productstorage.util.objectMappers

import com.edgar.commonlibrary.dtos.ProductDto
import com.edgar.productstorage.entities.ProductEntity
import com.fasterxml.jackson.databind.ObjectMapper

class ProductEntityObjectMapper(private val mapper: ObjectMapper) {

    /**
     * Convert a ProductEntity object to a ProductDto object.
     * @param productEntity the ProductEntity object that will be converted.
     * @return a ProductDto Object.
     */
    fun toProductDto(productEntity: ProductEntity): ProductDto {
        return mapper.convertValue(productEntity, ProductDto::class.java)
    }

}