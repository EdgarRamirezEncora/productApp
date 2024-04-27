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
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.rabbitmq.client.impl.AMQImpl.Basic.Reject
import lombok.extern.jackson.Jacksonized
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val productEntityObjectMapper: ProductEntityObjectMapper,
    private val mapper: ObjectMapper,
    private val rabbitTemplate: RabbitTemplate,
): ProductService {

    @RabbitListener(queues = [ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_QUEUE_NAME])
    override fun getAllProducts(message: Message): Result<List<ProductDto>, ErrorResponse> {
        val requestContent = String(message.body)
        val requestContentMap: Map<String, String> = mapper.readValue(requestContent)

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
    override fun addProduct(message: Message): Result<String, ErrorResponse> {
        val requestContent = String(message.body)
        val request: JsonNode = mapper.readTree(requestContent)

        val action = request.get("action").asText()

        if (action == "addProduct") {
            val newProduct = mapper.treeToValue(request.get("productData"), AddProductDto::class.java)
            productRepository.addProduct(newProduct.name, newProduct.description, newProduct.price)
            return Ok("The product ${newProduct.name} was added successfully.")
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
    override fun getProduct(message: Message): Result<ProductDto, ErrorResponse> {
        val requestContent = String(message.body)
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

    @RabbitListener(queues = [ConfigVariables.RABBITMQ_ADD_PRODUCT_DEAD_LETTER_QUEUE_NAME])
    override fun handleAddProductFailureMessage(message: Message) {
        val requestContent = String(message.body)
        val request: JsonNode = mapper.readTree(requestContent)

        val action = request.get("action").asText()

        if (action == "addProduct") {
            val newProduct = mapper.treeToValue(request.get("productData"), AddProductDto::class.java)
            productRepository.addProduct(newProduct.name, newProduct.description, newProduct.price)
        }
    }
}