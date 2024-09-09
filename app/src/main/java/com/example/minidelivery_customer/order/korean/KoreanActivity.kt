package com.example.minidelivery_customer.order.korean

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.databinding.ActivityKoreanBinding
import com.example.minidelivery_customer.order.OrderAdapter
import com.example.minidelivery_customer.order.OrderItem

class KoreanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_korean)

        setupToolbar()
        setupRecyclerView()
    }

    // Back Button : 이전화면으로 이동
    private fun setupToolbar() {
        // 뒤로가기 버튼 설정
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.cafeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@KoreanActivity)
            // OrderAdapter 설정 및 클릭 리스너 구현
            adapter = OrderAdapter(getFakeKoreanItems()) { koreanItem ->
                val intent = Intent(this@KoreanActivity, KoreanDetailActivity::class.java).apply {
                    putExtra("koreanItem", koreanItem)
                }
                startActivity(intent)
            }
        }
    }

    // 임시 한식 아이템 생성
    private fun getFakeKoreanItems(): List<OrderItem> {
        return listOf(
            OrderItem("한식", "3.7", "4,282", "1.1km", "무료배달", "27분", R.drawable.starbucks),
            OrderItem("한식", "4.4", "1,751", "1.6km", "2,000원", "42분", R.drawable.soseolwon),
            OrderItem("한식", "4.1", "689", "2.9km", "1,500원", "59분", R.drawable.saamann),
            OrderItem("한식", "4.7", "828", "4.8km", "무료배달", "61분", R.drawable.spectre),
            OrderItem("한식", "4.2", "1,296", "7.5km", "3,000원", "54분", R.drawable.samwonsac),
            OrderItem("한식", "4.9", "563", "2.4km", "무료배달", "34분", R.drawable.hywha)
        )
    }
}