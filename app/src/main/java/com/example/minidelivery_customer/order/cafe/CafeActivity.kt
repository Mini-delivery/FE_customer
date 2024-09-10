package com.example.minidelivery_customer.order.cafe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.databinding.ActivityCafeBinding
import com.example.minidelivery_customer.order.OrderAdapter
import com.example.minidelivery_customer.order.OrderItem

class CafeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.cafeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CafeActivity)
            adapter = OrderAdapter(getFakeCafeItems()) { cafeItem ->
                val intent = Intent(this@CafeActivity, CafeDetailActivity::class.java).apply {
                    putExtra("orderItem", cafeItem)
                }
                startActivity(intent)
            }
        }
    }

    // 임시 카페 아이템 생성 (실제 데이터로 대체 필요)
    private fun getFakeCafeItems(): List<OrderItem> {
        return listOf(
            OrderItem("스타벅스 한성대입구역점", "3.7", "4,282", "1.1km", "무료배달", "27분", R.drawable.starbucks, "서울특별시 성북구 동소문로 20"),
            OrderItem("소설원 삼선교점", "4.4", "1,751", "1.6km", "2,000원", "42분", R.drawable.soseolwon, "서울특별시 성북구 삼선교로 16길 116"),
            OrderItem("사만 커피 로스터스", "4.1", "689", "2.9km", "1,500원", "59분", R.drawable.saamann, "서울특별시 성북구 보문로 34길 77"),
            OrderItem("스펙터", "4.7", "828", "4.8km", "무료배달", "61분", R.drawable.spectre, "서울특별시 성북구 동소문로 20길 37-12"),
            OrderItem("삼원샏", "4.2", "1,296", "7.5km", "3,000원", "54분", R.drawable.samwonsac, "서울특별시 성북구 동소문로 20길 29-8"),
            OrderItem("커피한약집 혜화점", "4.9", "563", "2.4km", "무료배달", "34분", R.drawable.hywha, "서울특별시 종로구 대학로 11길 38")
        )
    }
}