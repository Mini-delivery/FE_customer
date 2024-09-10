package com.example.minidelivery_customer.order.korean

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.databinding.ActivityKoreanDetailBinding
import com.example.minidelivery_customer.order.CartItem
import com.example.minidelivery_customer.order.MenuAdapter
import com.example.minidelivery_customer.order.MenuItem
import com.example.minidelivery_customer.order.OrderItem
import com.example.minidelivery_customer.payment.PaymentActivity

class KoreanDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanDetailBinding
    private val cartItems = mutableMapOf<MenuItem, Int>()
    private lateinit var orderItem: OrderItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderItem = intent.getSerializableExtra("orderItem") as? OrderItem ?: run {
            Toast.makeText(this, "가게 정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupToolbar()
        setupStoreInfo()
        setupRecyclerView()
        setupOrderButton()
        updateCartCount()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupStoreInfo() {
        binding.detailImage.setImageResource(orderItem.imageResId)
        binding.storeName.text = orderItem.name
        binding.storeRating.text = "⭐ ${orderItem.rating} (${orderItem.reviews})"
    }

    private fun setupRecyclerView() {
        binding.menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@KoreanDetailActivity)
            adapter = MenuAdapter(getFakeKoreanMenuItems()) { menuItem ->
                addToCart(menuItem)
            }
        }
    }

    private fun addToCart(menuItem: MenuItem) {
        cartItems[menuItem] = cartItems.getOrDefault(menuItem, 0) + 1
        updateCartCount()
    }

    private fun updateCartCount() {
        val totalCount = cartItems.values.sum()
        binding.cartCount.text = totalCount.toString()
        binding.cartCount.visibility = if (totalCount > 0) View.VISIBLE else View.GONE
    }

    private fun setupOrderButton() {
        binding.orderButton.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            val cartItemList = cartItems.map { (item, quantity) ->
                CartItem(item.name, item.price, quantity, item.imageResId)
            }
            intent.putParcelableArrayListExtra("cartItems", ArrayList(cartItemList))
            intent.putExtra("orderItem", orderItem)
            startActivity(intent)
        }
    }

    private fun getFakeKoreanMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("백채 김치찌개", "9,000원", R.drawable.kimchi_stew),
            MenuItem("돼지김치찌개", "10,000원", R.drawable.pork_kimchi_stew),
            MenuItem("스팸김치찌개", "9,500원", R.drawable.spam_kimchi_stew),
            MenuItem("백채주물럭", "11,000원", R.drawable.jumuluck),
            MenuItem("스팸구이", "8,000원", R.drawable.spam),
            MenuItem("계란말이", "7,000원", R.drawable.egg_roll),
            MenuItem("라면사리", "3,500원", R.drawable.ramen_saegi),
            MenuItem("공기밥", "1,500원", R.drawable.rice)
        )
    }
}