package com.example.minidelivery_customer.orderhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.databinding.ItemOrderHistoryBinding
import java.text.SimpleDateFormat
import java.util.Locale

// 주문 내역을 RecyclerView에 표시하기 위한 어댑터
class OrderHistoryAdapter(
    private val orderHistory: List<OrderHistoryItem>, // 주문 내역 리스트
    private val onItemClick: (OrderHistoryItem) -> Unit // 아이템 클릭 시 실행할 람다 함수
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val binding = ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false) // 뷰 바인딩 객체 생성
        return OrderHistoryViewHolder(binding) // ViewHolder 반환
    }

    // ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        holder.bind(orderHistory[position]) // 해당 위치의 주문 내역 아이템 바인딩
    }

    // 전체 아이템 개수 반환
    override fun getItemCount() = orderHistory.size

    // 주문 내역 아이템을 표시하기 위한 ViewHolder
    inner class OrderHistoryViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 주문 내역 아이템 데이터 바인딩
        fun bind(orderHistoryItem: OrderHistoryItem) {
            binding.storeImage.setImageResource(orderHistoryItem.storeImage) // 가게 이미지 설정
            binding.storeName.text = orderHistoryItem.storeName // 가게 이름 설정
            binding.orderDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(orderHistoryItem.orderDate) // 주문 날짜 설정
            binding.totalPrice.text = "${orderHistoryItem.totalPrice}원" // 총 가격 설정

            // 아이템 클릭 리스너 설정
            binding.root.setOnClickListener {
                onItemClick(orderHistoryItem) // 클릭 시 람다 함수 실행
            }
        }
    }
}