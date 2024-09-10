package com.example.minidelivery_customer.order.chicken

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.databinding.ActivityChickenBinding
import com.example.minidelivery_customer.order.OrderAdapter
import com.example.minidelivery_customer.order.OrderItem

class ChickenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChickenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChickenBinding.inflate(layoutInflater)
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
        binding.chickenRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChickenActivity)
            adapter = OrderAdapter(getFakeChickenItems()) { chickenItem ->
                val intent = Intent(this@ChickenActivity, ChickenDetailActivity::class.java).apply {
                    putExtra("orderItem", chickenItem)
                }
                startActivity(intent)
            }
        }
    }

    private fun getFakeChickenItems(): List<OrderItem> {
        return listOf(
            OrderItem(
                "굽네치킨 삼선교점",
                "4.3",
                "1,628",
                "3.2km",
                "무료배달",
                "50분",
                R.drawable.goobne,
                "서울시 성북구 삼선교로 16길 116"
            ),
        )
    }
}
