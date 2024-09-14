package com.example.minidelivery_customer.api

data class OrderRequest(
    val store_id: String,
    val user_id: String,
    val order_name: String,
    val price: Int,
    val address: String
)
