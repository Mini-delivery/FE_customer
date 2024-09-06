package com.example.minidelivery_customer.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.databinding.ItemCartBinding
import com.example.minidelivery_customer.order.CartItem

class CartAdapter(
    private val cartItems: List<CartItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount() = cartItems.size

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            // 장바구니 아이템 정보 바인딩
            binding.itemImage.setImageResource(cartItem.imageResId)
            binding.itemName.text = cartItem.name
            binding.itemQuantity.text = "x ${cartItem.quantity}"
            binding.itemPrice.text = calculateTotalPrice(cartItem)
        }

        private fun calculateTotalPrice(cartItem: CartItem): String {
            val priceWithoutWon = cartItem.price.replace("[^0-9]".toRegex(), "").toInt()
            val totalPrice = priceWithoutWon * cartItem.quantity
            return "${totalPrice}원"
        }
    }
}