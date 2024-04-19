package com.edgar.productManagement.services

import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.commonlibrary.dtos.ProductDto

interface ProductService {
    /**
     * Send a message to the GetProductList Message queue requiring the action getProductList
     * and once it received the product list response it returns it to the controller.
     */
    fun getAllProducts() : List<ProductDto>
    fun getProductById(id: Long) : ProductDto
    /**
     * Send a message to the AddProduct Message queue to add a new product.
     * The message contains the Product data in JSON format that will be added.
     * @param addProductDto the product data that will be added.
     */
    fun addProduct(addProductDto: AddProductDto)
}