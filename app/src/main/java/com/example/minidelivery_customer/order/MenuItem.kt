package com.example.minidelivery_customer.order

// MenuItem 데이터 클래스 정의
data class MenuItem(
    val name: String,
    val price: String,
    val imageResId: Int
) : java.io.Serializable