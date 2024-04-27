package com.edgar.productManagement.services

import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.commonlibrary.dtos.ProductDto
import com.edgar.commonlibrary.util.ErrorResponse
import com.github.michaelbull.result.Result

interface ProductService {
    /**
     * Send a message to the GetProductList Message queue requiring the action getProductList
     * and once it received the product list response it returns it to the controller.
     */
    fun getAllProducts() : Result<List<ProductDto>, ErrorResponse>
    fun getProductById(id: Long) : Result<ProductDto, ErrorResponse>
    /**
     * Send a message to the AddProduct Message queue to add a new product.
     * The message contains the Product data in JSON format that will be added.
     * @param addProductDto the product data that will be added.
     */
    fun addProduct(addProductDto: AddProductDto): Result<Map<String, String>, ErrorResponse>
}