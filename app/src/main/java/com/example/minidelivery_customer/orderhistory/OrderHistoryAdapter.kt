package com.example.minidelivery_customer.orderhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.databinding.ItemOrderHistoryBinding
import java.text.SimpleDateFormat
import java.util.Locale

class OrderHistoryAdapter(
    private val orderHistory: List<OrderHistoryItem>,
    private val onItemClick: (OrderHistoryItem) -> Unit
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val binding = ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        holder.bind(orderHistory[position])
    }

    override fun getItemCount() = orderHistory.size

    inner class OrderHistoryViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderHistoryItem: OrderHistoryItem) {
            binding.storeImage.setImageResource(orderHistoryItem.storeImage)
            binding.storeName.text = orderHistoryItem.storeName
            binding.orderDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(orderHistoryItem.orderDate)
            binding.totalPrice.text = "${orderHistoryItem.totalPrice}원"

            binding.root.setOnClickListener {
                onItemClick(orderHistoryItem)
            }
        }
    }
}