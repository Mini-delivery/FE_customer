package com.example.minidelivery_customer.orderhistory

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// 주문 내역을 저장하고 불러오는 유틸리티 객체
object OrderHistoryUtil { // 싱글톤 패턴 : 앱 전체에서 하나의 인스턴스만 사용
    private const val PREFS_NAME = "OrderHistoryPrefs" // SharedPreferences 이름
    private const val ORDER_HISTORY_KEY = "order_history" // 주문 내역 저장 키

    // 주문 내역을 저장하는 함수 : shared_prefs/OrderHistoryPrefs.xml에 저장됨
    fun saveOrderHistory(context: Context, orderHistoryItem: OrderHistoryItem) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) // SharedPreferences 객체 생성
        val editor = sharedPreferences.edit() // SharedPreferences 편집기 생성

        val gson = Gson() // Gson 객체 생성
        val orderHistory = getOrderHistory(context).toMutableList() // 기존 주문 내역 가져오기
        orderHistory.add(0, orderHistoryItem) // 새 주문을 리스트의 맨 앞에 추가

        val json = gson.toJson(orderHistory) // 주문 내역을 JSON 문자열로 변환
        editor.putString(ORDER_HISTORY_KEY, json) // JSON 문자열 저장
        editor.apply() // 변경사항 적용
    }

    // 주문 내역을 불러오는 함수
    fun getOrderHistory(context: Context): List<OrderHistoryItem> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) // SharedPreferences 객체 생성
        val json = sharedPreferences.getString(ORDER_HISTORY_KEY, null) // JSON 문자열 불러오기

        return if (json != null) {
            val gson = Gson() // Gson 객체 생성
            val type = object : TypeToken<List<OrderHistoryItem>>() {}.type // List<OrderHistoryItem> 타입 정의
            gson.fromJson(json, type) // JSON을 List<OrderHistoryItem>으로 변환
        } else {
            emptyList() // JSON이 없으면 빈 리스트 반환
        }
    }
}