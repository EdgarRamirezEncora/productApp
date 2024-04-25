package com.edgar.productstorage.services.impl

import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.commonlibrary.dtos.ProductDto
import com.edgar.commonlibrary.util.ConfigVariables
import com.edgar.commonlibrary.util.ErrorMessages
import com.edgar.commonlibrary.util.ErrorResponse
import com.edgar.productstorage.repositories.ProductRepository
import com.edgar.productstorage.services.ProductService
import com.edgar.productstorage.util.objectMappers.ProductEntityObjectMapper
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val productEntityObjectMapper: ProductEntityObjectMapper,
    private val mapper: ObjectMapper,
): ProductService {

    @RabbitListener(queues = [ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_QUEUE_NAME])
    override fun getAllProducts(requestContent: String): Result<List<ProductDto>, ErrorResponse> {
        val requestContentMap: MutableMap<String, String> = mapper.readValue(requestContent)

        if (requestContentMap.containsKey("action") && requestContentMap.getValue("action") == "getAllProducts") {
            val products = productRepository
                                .getAllProducts()
                                .map { productEntityObjectMapper.toProductDto(it) }
            return Ok(products)
        }

        return Err(
            ErrorResponse(
                ErrorMessages.INVALID_REQUEST_ACTION_MESSAGE,
                statusCode = HttpStatus.BAD_REQUEST.value(),
                Date()
            )
        )
    }

    @RabbitListener(queues = [ConfigVariables.RABBITMQ_ADD_PRODUCT_QUEUE_NAME])
    override fun addProduct(requestContent: String): Result<Boolean, ErrorResponse> {
        val request: JsonNode = mapper.readTree(requestContent)

        val action = request.get("action").asText()
        val newProduct = mapper.treeToValue(request.get("productData"), AddProductDto::class.java)

        if(action == "addProduct") {
            productRepository.addProduct(newProduct.name, newProduct.description, newProduct.price)
            return Ok(true)
        }

        return Err(
            ErrorResponse(
                ErrorMessages.INVALID_REQUEST_ACTION_MESSAGE,
                statusCode = HttpStatus.BAD_REQUEST.value(),
                Date()
            )
        )
    }

    @RabbitListener(queues = [ConfigVariables.RABBITMQ_GET_PRODUCT_QUEUE_NAME])
    override fun getProduct(requestContent: String): Result<ProductDto, ErrorResponse> {
        val request: JsonNode = mapper.readTree(requestContent)

        val action = request.get("action").asText()
        val productId = request.get("productId").asLong()

        if(action == "getProduct") {
            return when(val product = productRepository.getProductById(productId)) {
                    null -> Err(ErrorResponse(
                        ErrorMessages.PRODUCT_NOT_FOUND_MESSAGE,
                        statusCode = HttpStatus.NOT_FOUND.value(),
                        Date()
                    ))
                    else -> Ok(productEntityObjectMapper.toProductDto(product))
                }
        }

        return Err(
            ErrorResponse(
                ErrorMessages.INVALID_REQUEST_ACTION_MESSAGE,
                statusCode = HttpStatus.BAD_REQUEST.value(),
                Date()
            )
        )
    }
}