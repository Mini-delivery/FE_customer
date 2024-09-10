package com.example.minidelivery_customer.order

// OrderItem 데이터 클래스 정의
data class OrderItem(
    val name: String,           // 가게 이름
    val rating: String,         // 별점
    val reviews: String,        // 리뷰 수
    val distance: String,       // 거리
    val deliveryFee: String,    // 배달비
    val deliveryTime: String,   // 배달 시간
    val imageResId: Int,        // 이미지 리소스 ID
    val address: String         // 가게 주소
) : java.io.Serializable

// Serializable 인터페이스 구현
// Serializable은 객체를 바이트 스트림으로 변환할 수 있게 해주는 마커 인터페이스
// 이를 통해 객체를 파일에 저장하거나 네트워크를 통해 전송할 수 있음
// 안드로이드에서는 Intent를 통해 액티비티 간에 객체를 전달할 때 주로 사용됨