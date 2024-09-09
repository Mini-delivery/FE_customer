package com.example.minidelivery_customer.payment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.databinding.ActivityPaymentBinding
import com.example.minidelivery_customer.order.CartItem
import java.text.NumberFormat
import java.util.Locale

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupCartItems()
        setupPaymentButton()

    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupCartItems() {
        val cartItems = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("cartItems", CartItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra<CartItem>("cartItems")
        } ?: return

        // RecyclerView 설정
        binding.cartItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PaymentActivity)
            adapter = CartAdapter(cartItems)
        }

        // 총 가격 계산 및 표시
        val totalPrice = cartItems.sumBy {
            it.price.replace("[^0-9]".toRegex(), "").toInt() * it.quantity
        }
        val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(totalPrice)
        binding.menuPrice.text = "${formattedPrice}원"
        binding.totalPrice.text = "${formattedPrice}원"
    }

    private fun setupPaymentButton() {
        binding.paymentButton.setOnClickListener {
            // PaidActivity로 이동
            // request

            val intent = Intent(this, PaidActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티를 종료
        }
    }
}