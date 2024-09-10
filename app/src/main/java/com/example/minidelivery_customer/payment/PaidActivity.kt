package com.example.minidelivery_customer.payment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.databinding.ActivityPaidBinding
import com.example.minidelivery_customer.home.HomeActivity
import com.example.minidelivery_customer.order.CartItem
import com.example.minidelivery_customer.order.OrderItem
import com.example.minidelivery_customer.orderhistory.OrderHistoryItem
import com.example.minidelivery_customer.orderhistory.OrderHistoryUtil
import java.util.*

class PaidActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaidBinding
    private lateinit var orderItem: OrderItem

    // Android 13(API 레벨 33) 이상만 가능
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaidBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Order Item 받아오기
        orderItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("orderItem", OrderItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("orderItem") as? OrderItem
        } ?: run {
            Toast.makeText(this, "주문 정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Cart Item 받아오기
        val cartItems = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("cartItems", ArrayList::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("cartItems")
        } as? ArrayList<*>

        val typedCartItems = cartItems?.filterIsInstance<CartItem>() ?: run {
            Toast.makeText(this, "장바구니 정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        saveOrderHistory(orderItem, typedCartItems)
        setupStoreName()
        setupConfirmButton()
    }

    // 주문내역 저장하기
    private fun saveOrderHistory(orderItem: OrderItem, cartItems: List<CartItem>) {
        val totalPrice = cartItems.sumBy { it.price.replace("[^0-9]".toRegex(), "").toInt() * it.quantity }
        val orderHistoryItem = OrderHistoryItem(
            orderId = UUID.randomUUID().toString(),
            orderDate = Date(),
            storeName = orderItem.name,
            storeImage = orderItem.imageResId,
            totalPrice = totalPrice,
            cartItems = cartItems
        )
        OrderHistoryUtil.saveOrderHistory(this, orderHistoryItem)
    }

    private fun setupStoreName() {
        binding.textView1.text = orderItem.name
    }

    private fun setupConfirmButton() {
        binding.button.setOnClickListener {
            // HomeActivity로 이동
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // 현재 액티비티를 종료
        }
    }
}