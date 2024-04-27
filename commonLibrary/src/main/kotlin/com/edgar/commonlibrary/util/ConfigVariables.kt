package com.edgar.commonlibrary.util

class ConfigVariables {
    companion object {
        const val RABBITMQ_ADD_PRODUCT_QUEUE_NAME = "add_product_queue"
        const val RABBITMQ_ADD_PRODUCT_EXCHANGE_NAME = "add_product_exchange"
        const val RABBITMQ_ADD_PRODUCT_ROUTING_KEY = "add_product_routing_key"

        const val RABBITMQ_GET_PRODUCT_LIST_QUEUE_NAME = "get_product_list_queue"
        const val RABBITMQ_GET_PRODUCT_LIST_EXCHANGE_NAME = "get_product_list_exchange"
        const val RABBITMQ_GET_PRODUCT_LIST_ROUTING_KEY = "get_product_list_routing_key"

        const val RABBITMQ_GET_PRODUCT_QUEUE_NAME = "get_product_queue"
        const val RABBITMQ_GET_PRODUCT_EXCHANGE_NAME = "get_product_exchange"
        const val RABBITMQ_GET_PRODUCT_ROUTING_KEY = "get_product_routing_key"

        const val RABBITMQ_ADD_PRODUCT_DEAD_LETTER_QUEUE_NAME = "add_product_dead_letter_queue"
        const val RABBITMQ_ADD_PRODUCT_DEAD_LETTER_QUEUE_EXCHANGE_NAME = "add_product_dead_letter_queue_exchange"
        const val RABBITMQ_ADD_PRODUCT_DEAD_LETTER_QUEUE_ROUTING_KEY = "add_product_dead_letter_queue_routing_key"
    }
}