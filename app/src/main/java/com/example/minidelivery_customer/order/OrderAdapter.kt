package com.example.minidelivery_customer.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.databinding.ItemCafeBinding

class OrderAdapter(
    private val orderItems: List<OrderItem>,
    private val onItemClick: (OrderItem) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemCafeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderItems[position])
    }

    override fun getItemCount() = orderItems.size

    inner class OrderViewHolder(private val binding: ItemCafeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderItem: OrderItem) {
            // 카페 아이템 정보 바인딩
            binding.cafeName.text = orderItem.name
            binding.cafeImage.setImageResource(orderItem.imageResId)
            binding.cafeRating.text = "★ ${orderItem.rating}"
            binding.cafeReviews.text = "(${orderItem.reviews})"
            binding.cafeDistance.text = orderItem.distance
            binding.cafeDeliveryFee.text = orderItem.deliveryFee
            binding.cafeDeliveryTime.text = orderItem.deliveryTime

            // 아이템 클릭 리스너 설정
            binding.root.setOnClickListener {
                onItemClick(orderItem)
            }
        }
    }
}