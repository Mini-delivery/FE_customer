package com.example.minidelivery_customer.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.databinding.ItemStoreBinding

class OrderAdapter(
    private val orderItems: List<OrderItem>,
    private val onItemClick: (OrderItem) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderItems[position])
    }

    override fun getItemCount() = orderItems.size

    inner class OrderViewHolder(private val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderItem: OrderItem) {
            // 카페 아이템 정보 바인딩
            binding.storeName.text = orderItem.name
            binding.storeImage.setImageResource(orderItem.imageResId)
            binding.storeRating.text = "★ ${orderItem.rating}"
            binding.storeReviews.text = "(${orderItem.reviews})"
            binding.storeDistance.text = orderItem.distance
            binding.storeDeliveryFee.text = orderItem.deliveryFee
            binding.storeDeliveryTime.text = orderItem.deliveryTime

            // 아이템 클릭 리스너 설정
            binding.root.setOnClickListener {
                onItemClick(orderItem)
            }
        }
    }
}