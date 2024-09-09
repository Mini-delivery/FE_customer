package com.example.minidelivery_customer.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/jwt-login/join")
    fun registerUser(@Body signupRequest: SignupRequest): Call<SignupResponse>

    @POST("/jwt-login/login")
    fun loginUser(@Body signinRequest: SigninRequest): Call<SigninResponse>

}