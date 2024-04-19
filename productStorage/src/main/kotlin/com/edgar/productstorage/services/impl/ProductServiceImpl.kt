package com.edgar.productstorage.services.impl

import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.commonlibrary.dtos.ProductDto
import com.edgar.commonlibrary.util.ConfigVariables
import com.edgar.productstorage.repositories.ProductRepository
import com.edgar.productstorage.services.ProductService
import com.edgar.productstorage.util.objectMappers.ProductEntityObjectMapper
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val productEntityObjectMapper: ProductEntityObjectMapper,
    private val mapper: ObjectMapper,
): ProductService {

    @RabbitListener(queues = [ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_QUEUE_NAME])
    override fun getAllProducts(requestContent: String): List<ProductDto>? {
        val requestContentMap: MutableMap<String, String> = mapper.readValue(requestContent)

        if(requestContentMap.containsKey("action") && requestContentMap.getValue("action") == "getAllProducts") {
            return productRepository
                        .getAllProducts()
                        .map { productEntityObjectMapper.toProductDto(it) }
        }

        return null
    }

    @RabbitListener(queues = [ConfigVariables.RABBITMQ_ADD_PRODUCT_QUEUE_NAME])
    override fun addProduct(requestContent: String): Boolean {
        val request: JsonNode = mapper.readTree(requestContent)

        val action = request.get("action").asText()
        val newProduct = mapper.treeToValue(request.get("productData"), AddProductDto::class.java)
        println(action)

        if(action == "addProduct") {
            productRepository.addProduct(newProduct.name, newProduct.description, newProduct.price)
        }

        return true
    }

    @RabbitListener(queues = [ConfigVariables.RABBITMQ_GET_PRODUCT_QUEUE_NAME])
    override fun getProduct(requestContent: String): ProductDto? {
        val request: JsonNode = mapper.readTree(requestContent)

        val action = request.get("action").asText()
        val productId = request.get("productId").asLong()

        if(action == "getProduct") {
            val product = productRepository.getProductById(productId)

            if(product != null) {
                return productEntityObjectMapper.toProductDto(product)
            }
        }

        return null
    }
}