package com.edgar.productManagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductManagementApplication

fun main(args: Array<String>) {
	runApplication<ProductManagementApplication>(*args)
}
