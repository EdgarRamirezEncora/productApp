package com.edgar.productManagement.services

import com.edgar.commonlibrary.deserializers.ErrorResponseDeserializer
import com.edgar.commonlibrary.deserializers.ProductDtoDeserializer
import com.edgar.commonlibrary.dtos.ProductDto

import com.edgar.productManagement.services.impl.ProductServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.mockk
import org.springframework.amqp.rabbit.core.RabbitTemplate
import java.math.BigDecimal

class ProductServiceTests {
    private val rabbitTemplate: RabbitTemplate = mockk()
    private val objectMapper: ObjectMapper = mockk()
    private val productDtoDeserializer: ProductDtoDeserializer = mockk()
    private val errorResponseDeserializer: ErrorResponseDeserializer = mockk()
    private val productService: ProductService = ProductServiceImpl(rabbitTemplate, objectMapper, productDtoDeserializer, errorResponseDeserializer)

    private val productList = mutableListOf(
        ProductDto(1, "Product 1", "Description 1", BigDecimal("1.0")),
        ProductDto(2, "Product 2", "Description 2", BigDecimal("2.0")),
        ProductDto(2, "Product 2", "Description 3", BigDecimal("3.0"))
    )
}