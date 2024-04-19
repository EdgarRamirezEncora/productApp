package com.edgar.productManagement.services.impl

import com.edgar.commonlibrary.deserializers.ProductDtoDeserializer
import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.commonlibrary.dtos.ProductDto
import com.edgar.commonlibrary.exceptions.InvalidProductException
import com.edgar.commonlibrary.exceptions.InvalidProductListException
import com.edgar.commonlibrary.util.ConfigVariables
import com.edgar.productManagement.services.ProductService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.Valid
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import com.edgar.commonlibrary.exceptions.ProductNotFoundException
import com.edgar.commonlibrary.util.ErrorMessages

@Service
class ProductServiceImpl(
    private val rabbitTemplate: RabbitTemplate,
    private val objectMapper: ObjectMapper,
    private val productDtoDeserializer: ProductDtoDeserializer
) : ProductService {
    val productList = mutableListOf(
        ProductDto(1, "Product 1", "Description 1", BigDecimal("1.0")),
        ProductDto(2, "Product 2", "Description 2", BigDecimal("2.0")),
        ProductDto(2, "Product 2", "Description 3", BigDecimal("3.0"))
    )

    override fun getAllProducts() : List<ProductDto> {
        val request = mapOf("action" to "getAllProducts")

        val response = rabbitTemplate.convertSendAndReceive(
            ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_EXCHANGE_NAME,
            ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_ROUTING_KEY,
            objectMapper.writeValueAsString(request)
        )

        if (response == null) {
            throw InvalidProductListException(ErrorMessages.INVALID_PRODUCT_LIST_MESSAGE)
        }

        return productDtoDeserializer.deserializeProductList(response)
    }

    override fun getProductById(id : Long) : ProductDto {
        val request = mapOf("action" to "getProduct", "productId" to id)

        val response = rabbitTemplate.convertSendAndReceive(
            ConfigVariables.RABBITMQ_GET_PRODUCT_EXCHANGE_NAME,
            ConfigVariables.RABBITMQ_GET_PRODUCT_ROUTING_KEY,
            objectMapper.writeValueAsString(request)
        )

        if (response == null) {
            throw ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_MESSAGE)
        }


        return productDtoDeserializer.deserializeProductDto(response)
    }

    override fun addProduct(addProductDto: AddProductDto) {
        val request = mapOf("action" to "addProduct", "productData" to addProductDto)

        val response = rabbitTemplate.convertSendAndReceive(
            ConfigVariables.RABBITMQ_ADD_PRODUCT_EXCHANGE_NAME,
            ConfigVariables.RABBITMQ_ADD_PRODUCT_ROUTING_KEY,
            objectMapper.writeValueAsString(request)
        )
    }
}