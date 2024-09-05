package com.example.minidelivery_customer.order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        // 뒤로가기 버튼 설정
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailActivity)
            // CafeAdapter 설정 및 클릭 리스너 구현
            adapter = MenuAdapter(getFakeMenuItems()) { menuItem ->
                val intent =
                    Intent(this@OrderDetailActivity, OrderDetailActivity::class.java).apply {
                        putExtra("menuItem", menuItem)
                    }
                startActivity(intent)
            }
        }
    }

    // 결제 버튼 클릭 이벤트 처리
    private fun setupOrderButton() {
        binding.orderButton.setOnClickListener {
            // 장바구니에 추가하는 로직 구현
            // 예: 현재 메뉴 아이템을 장바구니에 추가
        }
    }
}

// 임시 메뉴 아이템 생성 (실제 데이터로 대체 필요)
private fun getFakeMenuItems(): List<MenuItem> {
    return listOf(
        MenuItem("Iced Caffe Americano", "4,500원", R.drawable.americano),
        MenuItem("Classic Affogato", "5,500원", R.drawable.affogato),
        MenuItem("Sparkling Citrus Espresso", "7,500원", R.drawable.coffe_sparkling),
        MenuItem("Shakerato Bianco", "8,500원", R.drawable.latte_shakerato),
        MenuItem("Lavender Cafe Breve", "7,000원", R.drawable.latte_lavender),
        MenuItem("Iced Black Glazed Latte", "6,700원", R.drawable.latte_glazed),
        MenuItem("French Vanilla Latte", "7,200원", R.drawable.latte_french),
        MenuItem("Mango Passion Tea Blended", "6,800원", R.drawable.shake_passion),
        MenuItem("Mango Banana Blended", "6,800원", R.drawable.shake_mango)
    )
}