package com.edgar.productManagement.services

import com.edgar.commonlibrary.deserializers.ProductDtoDeserializer
import com.edgar.commonlibrary.dtos.ProductDto
import com.edgar.commonlibrary.exceptions.InvalidProductListException
import com.edgar.commonlibrary.util.ConfigVariables
import com.edgar.productManagement.services.impl.ProductServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.amqp.rabbit.core.RabbitTemplate
import java.math.BigDecimal

class ProductServiceTests {
    private val rabbitTemplate: RabbitTemplate = mockk()
    private val objectMapper: ObjectMapper = mockk()
    private val productDtoDeserializer: ProductDtoDeserializer = mockk()
    private val productService: ProductService = ProductServiceImpl(rabbitTemplate, objectMapper, productDtoDeserializer)

    private val productList = mutableListOf(
        ProductDto(1, "Product 1", "Description 1", BigDecimal("1.0")),
        ProductDto(2, "Product 2", "Description 2", BigDecimal("2.0")),
        ProductDto(2, "Product 2", "Description 3", BigDecimal("3.0"))
    )


    @Test
    fun getAllProductsTest_success() {
        val request = mapOf("action" to "getAllProducts")

        every {
            rabbitTemplate.convertSendAndReceive(
                ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_EXCHANGE_NAME,
                ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_ROUTING_KEY,
                objectMapper.writeValueAsString(request)
            )
        } returns productList
        every { objectMapper.writeValueAsString(request) } returns ""
        every { productDtoDeserializer.deserializeProductList(any()) } returns productList


        val result = productService.getAllProducts()

        Assertions.assertTrue(result.containsAll(productList))
        Assertions.assertEquals(result, productList)
        Assertions.assertEquals(result.size, productList.size)
    }

    @Test
    fun getAllProductsTest_failure() {
        val request = mapOf("action" to "getAllProducts")

        every {
            rabbitTemplate.convertSendAndReceive(
                ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_EXCHANGE_NAME,
                ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_ROUTING_KEY,
                objectMapper.writeValueAsString(request)
            )
        } returns null
        every { objectMapper.writeValueAsString(request) } returns ""

        Assertions.assertThrows(InvalidProductListException::class.java) { productService.getAllProducts() }
    }
}