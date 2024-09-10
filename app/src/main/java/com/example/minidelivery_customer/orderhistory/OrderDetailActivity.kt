package com.example.minidelivery_customer.orderhistory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.databinding.ActivityOrderDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale

// 주문 상세 정보를 표시하는 액티비티
class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding // 뷰 바인딩 객체

    // 액티비티 생성 시 호출되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // 상위 클래스의 onCreate 호출
        binding = ActivityOrderDetailBinding.inflate(layoutInflater) // 뷰 바인딩 초기화
        setContentView(binding.root) // 레이아웃 설정

        // Intent에서 주문 내역 아이템 가져오기
        val orderHistoryItem = intent.getSerializableExtra("orderHistoryItem") as? OrderHistoryItem
        if (orderHistoryItem == null) { // 주문 내역 아이템이 없으면 액티비티 종료
            finish()
            return
        }

        setupToolbar() // 툴바 설정
        displayOrderDetails(orderHistoryItem) // 주문 상세 정보 표시
    }

    // 툴바를 설정하는 함수
    private fun setupToolbar() {
        binding.backButton.setOnClickListener { // 뒤로 가기 버튼 클릭 리스너 설정
            onBackPressedDispatcher.onBackPressed() // 뒤로 가기 동작 수행
        }
    }

    // 주문 상세 정보를 화면에 표시하는 함수
    private fun displayOrderDetails(orderHistoryItem: OrderHistoryItem) {
        binding.storeImage.setImageResource(orderHistoryItem.storeImage) // 가게 이미지 설정
        binding.storeName.text = orderHistoryItem.storeName // 가게 이름 설정
        binding.orderDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(orderHistoryItem.orderDate) // 주문 날짜 설정
        binding.totalPrice.text = "${orderHistoryItem.totalPrice}원" // 총 가격 설정

        // 주문 아이템 RecyclerView 설정
        binding.orderItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailActivity) // 레이아웃 매니저 설정
            adapter = OrderItemAdapter(orderHistoryItem.cartItems) // 어댑터 설정
        }
    }
}