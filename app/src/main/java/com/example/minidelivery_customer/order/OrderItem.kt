package com.example.minidelivery_customer.order

// OrderItem 데이터 클래스 정의
data class OrderItem(
    val name: String,
    val rating: String,
    val reviews: String,
    val distance: String,
    val deliveryFee: String,
    val deliveryTime: String,
    val imageResId: Int
) : java.io.Serializable