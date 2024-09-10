package com.example.minidelivery_customer.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class CartItem(
    val name: String,
    val price: String,
    val quantity: Int,
    val imageResId: Int
) : Parcelable, Serializable