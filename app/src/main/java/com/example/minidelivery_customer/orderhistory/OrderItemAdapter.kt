package com.example.minidelivery_customer.orderhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.databinding.ItemOrderDetailBinding
import com.example.minidelivery_customer.order.CartItem

class OrderItemAdapter(private val orderItems: List<CartItem>) :
    RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(orderItems[position])
    }

    override fun getItemCount() = orderItems.size

    class OrderItemViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.itemImage.setImageResource(cartItem.imageResId)
            binding.itemName.text = cartItem.name
            binding.itemQuantity.text = "${cartItem.quantity}개"
            binding.itemPrice.text = cartItem.price
        }
    }
}