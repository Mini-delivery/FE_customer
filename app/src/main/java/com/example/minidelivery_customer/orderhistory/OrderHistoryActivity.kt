package com.example.minidelivery_customer.orderhistory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.databinding.ActivityOrderHistoryBinding

// 주문 내역을 표시하는 액티비티
class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding // 뷰 바인딩 객체

    // 액티비티 생성 시 호출되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // 상위 클래스의 onCreate 호출
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater) // 뷰 바인딩 초기화
        setContentView(binding.root) // 레이아웃 설정

        setupToolbar() // 툴바 설정
        setupRecyclerView() // RecyclerView 설정
    }

    // 툴바를 설정하는 함수
    private fun setupToolbar() {
        binding.backButton.setOnClickListener { // 뒤로 가기 버튼 클릭 리스너 설정
            onBackPressedDispatcher.onBackPressed() // 뒤로 가기 동작 수행
        }
    }

    // RecyclerView를 설정하는 함수
    private fun setupRecyclerView() {
        val orderHistory = OrderHistoryUtil.getOrderHistory(this) // 주문 내역 가져오기
        binding.orderHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderHistoryActivity) // 레이아웃 매니저 설정
            adapter = OrderHistoryAdapter(orderHistory) { orderHistoryItem -> // 어댑터 설정 및 클릭 리스너 구현
                val intent = Intent(this@OrderHistoryActivity, OrderDetailActivity::class.java) // 주문 상세 액티비티로 이동하는 인텐트 생성
                intent.putExtra("orderHistoryItem", orderHistoryItem) // 선택된 주문 내역 아이템 전달
                startActivity(intent) // 주문 상세 액티비티 시작
            }
        }
    }
}