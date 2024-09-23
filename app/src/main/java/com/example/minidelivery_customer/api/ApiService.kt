package com.example.minidelivery_customer.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class UpdateUserRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("address") val address: String
)

data class UpdateUserResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)

data class UserInfoResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: UserData?
)

data class UserData(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("address") val address: String,
    @SerializedName("email") val email: String
)


interface ApiService {
    @POST("/jwt-login/join")
    fun registerUser(@Body signupRequest: SignupRequest): Call<SignupResponse>

    @POST("/jwt-login/login")
    fun loginUser(@Body signinRequest: SigninRequest): Call<SigninResponse>

    // my page user info
    @GET("user/info")
    fun getUserInfo(): Call<UserInfoResponse>

    // my page user info update
    @POST("api/update")
    fun updateUser(@Body request: UpdateUserRequest): Call<UpdateUserResponse>

    @POST("/api/orders")
    fun sendOrder(@Body orderRequest: OrderRequest): Call<Void>

}