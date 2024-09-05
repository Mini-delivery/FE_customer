package com.example.minidelivery_customer.order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
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
        binding.cafeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderActivity)
            // CafeAdapter 설정 및 클릭 리스너 구현
            adapter = CafeAdapter(getFakeCafeItems()) { cafeItem ->
                val intent = Intent(this@OrderActivity, OrderDetailActivity::class.java).apply {
                    putExtra("cafeItem", cafeItem)
                }
                startActivity(intent)
            }
        }
    }

    // 임시 카페 아이템 생성 (실제 데이터로 대체 필요)
    private fun getFakeCafeItems(): List<CafeItem> {
        return listOf(
            CafeItem("스타벅스 한성대입구역점", "3.7", "4,282", "1.1km", "무료배달", "27분", R.drawable.starbucks),
            CafeItem("소설원 삼선교점", "4.4", "1,751", "1.6km", "2,000원", "42분", R.drawable.soseolwon),
            CafeItem("사만 커피 로스터스", "4.1", "689", "2.9km", "1,500원", "59분", R.drawable.saamann),
            CafeItem("스펙터", "4.7", "828", "4.8km", "무료배달", "61분", R.drawable.spectre),
            CafeItem("삼원샏", "4.2", "1,296", "7.5km", "3,000원", "54분", R.drawable.samwonsac),
            CafeItem("커피한약집 혜화점", "4.9", "563", "2.4km", "무료배달", "34분", R.drawable.hywha)
        )
    }
}


