package com.edgar.productManagement.config

import com.edgar.commonlibrary.deserializers.ProductDtoDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun productDtoDeserializer(): ProductDtoDeserializer {
        return ProductDtoDeserializer()
    }
}