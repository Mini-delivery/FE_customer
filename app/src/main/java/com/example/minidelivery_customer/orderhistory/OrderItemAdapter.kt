package com.example.minidelivery_customer.orderhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.databinding.ItemOrderDetailBinding
import com.example.minidelivery_customer.order.CartItem

// 주문 상세 내역의 각 아이템을 RecyclerView에 표시하기 위한 어댑터
class OrderItemAdapter(private val orderItems: List<CartItem>) :
    RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false) // 뷰 바인딩 객체 생성
        return OrderItemViewHolder(binding) // ViewHolder 반환
    }

    // ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(orderItems[position]) // 해당 위치의 주문 아이템 바인딩
    }

    // 전체 아이템 개수 반환
    override fun getItemCount() = orderItems.size

    // 주문 아이템을 표시하기 위한 ViewHolder
    class OrderItemViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 주문 아이템 데이터 바인딩
        fun bind(cartItem: CartItem) {
            binding.itemImage.setImageResource(cartItem.imageResId) // 아이템 이미지 설정
            binding.itemName.text = cartItem.name // 아이템 이름 설정
            binding.itemQuantity.text = "${cartItem.quantity}개" // 아이템 수량 설정
            binding.itemPrice.text = cartItem.price // 아이템 가격 설정
        }
    }
}