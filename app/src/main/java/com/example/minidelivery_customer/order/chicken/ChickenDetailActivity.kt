package com.example.minidelivery_customer.order.chicken

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.databinding.ActivityChickenDetailBinding
import com.example.minidelivery_customer.order.CartItem
import com.example.minidelivery_customer.order.MenuAdapter
import com.example.minidelivery_customer.order.MenuItem
import com.example.minidelivery_customer.order.OrderItem
import com.example.minidelivery_customer.payment.PaymentActivity

class ChickenDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChickenDetailBinding
    private val cartItems = mutableMapOf<MenuItem, Int>()
    private lateinit var orderItem: OrderItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChickenDetailBinding.inflate(layoutInflater)
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
            layoutManager = LinearLayoutManager(this@ChickenDetailActivity)
            adapter = MenuAdapter(getFakeChickenMenuItems()) { menuItem ->
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

    private fun getFakeChickenMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("굽네 오리지널", "16,000원", R.drawable.goobne_original),
            MenuItem("굽네 고추바사삭", "17,000원", R.drawable.goobne_gochujang),
            MenuItem("굽네 치즈바사삭", "19,000원", R.drawable.goobne_cheese),
            MenuItem("굽네 갈비천왕", "18,000원", R.drawable.goobne_galbi),
            MenuItem("굽네 볼케이노", "18,000원", R.drawable.goobne_volcano),
            MenuItem("폭립", "18,000원", R.drawable.ribs),
            MenuItem("트리플 포테이토 피자", "14,000원", R.drawable.potato_pizza),
            MenuItem("바질&토마토 피자", "9,000원", R.drawable.bazil_pizza),
            MenuItem("시카고 딥디쉬 피자", "18,000원", R.drawable.chicago_pizza)
        )
    }
}