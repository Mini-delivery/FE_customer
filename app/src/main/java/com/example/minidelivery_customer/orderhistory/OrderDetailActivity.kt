package com.example.minidelivery_customer.orderhistory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.databinding.ActivityOrderDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderHistoryItem = intent.getSerializableExtra("orderHistoryItem") as? OrderHistoryItem
        if (orderHistoryItem == null) {
            finish()
            return
        }

        setupToolbar()
        displayOrderDetails(orderHistoryItem)
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun displayOrderDetails(orderHistoryItem: OrderHistoryItem) {
        binding.storeImage.setImageResource(orderHistoryItem.storeImage)
        binding.storeName.text = orderHistoryItem.storeName
        binding.orderDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(orderHistoryItem.orderDate)
        binding.totalPrice.text = "${orderHistoryItem.totalPrice}원"

        binding.orderItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailActivity)
            adapter = OrderItemAdapter(orderHistoryItem.cartItems)
        }
    }
}