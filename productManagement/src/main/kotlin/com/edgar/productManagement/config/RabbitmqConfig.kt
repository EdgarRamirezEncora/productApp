package com.edgar.productManagement.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.edgar.commonlibrary.util.ConfigVariables
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter

@Configuration
class RabbitmqConfig {

    @Bean
    fun addProductQueue(): Queue = Queue(
        ConfigVariables.RABBITMQ_ADD_PRODUCT_QUEUE_NAME,
        true,
        false,
        false,
        mapOf(
            "x-dead-letter-exchange" to ConfigVariables.RABBITMQ_ADD_PRODUCT_DEAD_LETTER_QUEUE_EXCHANGE_NAME,
            "x-dead-letter-routing-key" to ConfigVariables.RABBITMQ_ADD_PRODUCT_DEAD_LETTER_QUEUE_ROUTING_KEY
        )
    )

    @Bean
    fun addProductExchange(): TopicExchange = TopicExchange(ConfigVariables.RABBITMQ_ADD_PRODUCT_EXCHANGE_NAME)

    @Bean
    fun addProductBinding(): Binding = BindingBuilder
                                            .bind(addProductQueue())
                                            .to(addProductExchange())
                                            .with(ConfigVariables.RABBITMQ_ADD_PRODUCT_ROUTING_KEY)

    @Bean
    fun addProductDeadLetterQueue(): Queue = QueueBuilder
                                            .durable(ConfigVariables.RABBITMQ_ADD_PRODUCT_DEAD_LETTER_QUEUE_NAME)
                                            .build()

    @Bean
    fun addProductDeadLetterQueueExchange(): DirectExchange = DirectExchange(ConfigVariables.RABBITMQ_ADD_PRODUCT_DEAD_LETTER_QUEUE_EXCHANGE_NAME)

    @Bean
    fun addProductDeadLetterQueueBinding(): Binding = BindingBuilder
                                                        .bind(addProductDeadLetterQueue())
                                                        .to(addProductDeadLetterQueueExchange())
                                                        .with(ConfigVariables.RABBITMQ_ADD_PRODUCT_DEAD_LETTER_QUEUE_ROUTING_KEY)

    @Bean
    fun getProductListQueue(): Queue = Queue(ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_QUEUE_NAME)

    @Bean
    fun getProductListExchange(): TopicExchange = TopicExchange(ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_EXCHANGE_NAME)

    @Bean
    fun getProductListBinding(): Binding = BindingBuilder
                                            .bind(getProductListQueue())
                                            .to(getProductListExchange())
                                            .with(ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_ROUTING_KEY)

    @Bean
    fun getProductQueue(): Queue = Queue(ConfigVariables.RABBITMQ_GET_PRODUCT_QUEUE_NAME)

    @Bean
    fun getProductExchange(): TopicExchange = TopicExchange(ConfigVariables.RABBITMQ_GET_PRODUCT_EXCHANGE_NAME)

    @Bean
    fun getProductBinding(): Binding = BindingBuilder
                                        .bind(getProductQueue())
                                        .to(getProductExchange())
                                        .with(ConfigVariables.RABBITMQ_GET_PRODUCT_ROUTING_KEY)

    @Bean
    fun messageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun amqpTemplate(connectionFactory: ConnectionFactory): AmqpTemplate {
        val amqpTemplate = RabbitTemplate(connectionFactory)
        amqpTemplate.messageConverter = messageConverter()
        return amqpTemplate
    }
}