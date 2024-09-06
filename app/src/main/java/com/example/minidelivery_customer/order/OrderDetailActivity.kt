package com.example.minidelivery_customer.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.databinding.ActivityOrderDetailBinding
import com.example.minidelivery_customer.payment.PaymentActivity

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    private val cartItems = mutableMapOf<MenuItem, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupOrderButton()
        updateCartCount()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailActivity)
            adapter = MenuAdapter(getFakeMenuItems()) { menuItem ->
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
            startActivity(intent)
        }
    }

    private fun getFakeMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("Iced Caffe Americano", "4,500원", R.drawable.americano),
            MenuItem("Classic Affogato", "5,500원", R.drawable.affogato),
            MenuItem("Sparkling Citrus Espresso", "7,500원", R.drawable.coffe_sparkling),
            MenuItem("Shakerato Bianco", "8,500원", R.drawable.latte_shakerato),
            MenuItem("Lavender Cafe Breve", "7,000원", R.drawable.latte_lavender),
            MenuItem("Iced Black Glazed Latte", "6,700원", R.drawable.latte_glazed),
            MenuItem("French Vanilla Latte", "7,200원", R.drawable.latte_french),
            MenuItem("Mango Passion Tea Blended", "6,800원", R.drawable.shake_passion),
            MenuItem("Mango Banana Blended", "6,800원", R.drawable.shake_mango)
        )
    }
}