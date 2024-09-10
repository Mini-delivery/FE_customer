package com.example.minidelivery_customer.orderhistory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
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
        val orderHistory = OrderHistoryUtil.getOrderHistory(this)
        binding.orderHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderHistoryActivity)
            adapter = OrderHistoryAdapter(orderHistory) { orderHistoryItem ->
                val intent = Intent(this@OrderHistoryActivity, OrderDetailActivity::class.java)
                intent.putExtra("orderHistoryItem", orderHistoryItem)
                startActivity(intent)
            }
        }
    }
}