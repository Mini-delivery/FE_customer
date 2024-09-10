package com.example.minidelivery_customer.orderhistory

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object OrderHistoryUtil {
    private const val PREFS_NAME = "OrderHistoryPrefs"
    private const val ORDER_HISTORY_KEY = "order_history"

    fun saveOrderHistory(context: Context, orderHistoryItem: OrderHistoryItem) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val orderHistory = getOrderHistory(context).toMutableList()
        orderHistory.add(0, orderHistoryItem) // 새 주문을 리스트의 맨 앞에 추가

        val json = gson.toJson(orderHistory)
        editor.putString(ORDER_HISTORY_KEY, json)
        editor.apply()
    }

    fun getOrderHistory(context: Context): List<OrderHistoryItem> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(ORDER_HISTORY_KEY, null)

        return if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<List<OrderHistoryItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}