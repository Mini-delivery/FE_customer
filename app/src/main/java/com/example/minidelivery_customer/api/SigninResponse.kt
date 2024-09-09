package com.example.minidelivery_customer.api

data class SigninResponse(
    val loginId: String,
    val nickname: String,
    val accessToken: String,
    val success: Boolean,
    val message: String
)
