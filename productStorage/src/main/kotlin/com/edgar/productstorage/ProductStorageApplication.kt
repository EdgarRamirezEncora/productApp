package com.edgar.productstorage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductStorageApplication

fun main(args: Array<String>) {
    runApplication<ProductStorageApplication>(*args)
}
