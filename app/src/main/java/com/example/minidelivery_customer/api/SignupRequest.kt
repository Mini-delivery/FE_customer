package com.example.minidelivery_customer.api

data class SignupRequest (
    val loginId: String,
    val password: String,
    val passwordCheck: String,
    val address: String,
    val nickname: String
)