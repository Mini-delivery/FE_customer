package com.example.minidelivery_customer.order.korean

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.koreanRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@KoreanActivity)
            adapter = OrderAdapter(getFakeKoreanItems()) { koreanItem ->
                val intent = Intent(this@KoreanActivity, KoreanDetailActivity::class.java).apply {
                    putExtra("orderItem", koreanItem)
                }
                startActivity(intent)
            }
        }
    }

    private fun getFakeKoreanItems(): List<OrderItem> {
        return listOf(
            OrderItem("백채김치찌개 한성대점", "4.3", "2,982", "0.7km", "무료배달", "30분", R.drawable.back_kimchi, "서울시 성북구 동소문로 20"),
            OrderItem("청년감자탕 성북점", "4.6", "3,151", "1.5km", "1,000원", "38분", R.drawable.gamjatang, "서울시 성북구 성북로 13길 5"),
            OrderItem("엄마손칼국수 동소문점", "4.2", "1,789", "2.3km", "2,000원", "42분", R.drawable.kalguksu, "서울시 성북구 동소문로 26길 33"),
            OrderItem("할매순대국 삼선교점", "4.4", "2,228", "3.0km", "무료배달", "47분", R.drawable.sundaeguk, "서울시 성북구 삼선교로 16길 116"),
            OrderItem("대한곱창 혜화점", "4.5", "1,896", "4.2km", "2,500원", "52분", R.drawable.gopchang, "서울시 종로구 대학로 11길 38"),
            OrderItem("귀한족발 성북점", "4.1", "1,663", "2.6km", "무료배달", "40분", R.drawable.jokbal, "서울시 성북구 성북로 19")
        )
    }
}