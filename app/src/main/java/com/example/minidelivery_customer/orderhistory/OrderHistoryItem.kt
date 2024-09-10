package com.example.minidelivery_customer.orderhistory

import com.example.minidelivery_customer.order.CartItem
import java.io.Serializable
import java.util.Date

data class OrderHistoryItem(
    val orderId: String,
    val orderDate: Date,
    val storeName: String,
    val storeImage: Int,
    val totalPrice: Int,
    val cartItems: List<CartItem>
) : Serializable