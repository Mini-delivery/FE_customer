package com.example.minidelivery_customer.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.databinding.ItemCafeBinding

class CafeAdapter(
    private val cafeItems: List<CafeItem>,
    private val onItemClick: (CafeItem) -> Unit
) : RecyclerView.Adapter<CafeAdapter.CafeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeViewHolder {
        val binding = ItemCafeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CafeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
        holder.bind(cafeItems[position])
    }

    override fun getItemCount() = cafeItems.size

    inner class CafeViewHolder(private val binding: ItemCafeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cafeItem: CafeItem) {
            // 카페 아이템 정보 바인딩
            binding.cafeName.text = cafeItem.name
            binding.cafeImage.setImageResource(cafeItem.imageResId)
            binding.cafeRating.text = "★ ${cafeItem.rating}"
            binding.cafeReviews.text = "(${cafeItem.reviews})"
            binding.cafeDistance.text = cafeItem.distance
            binding.cafeDeliveryFee.text = cafeItem.deliveryFee
            binding.cafeDeliveryTime.text = cafeItem.deliveryTime

            // 아이템 클릭 리스너 설정
            binding.root.setOnClickListener {
                onItemClick(cafeItem)
            }
        }
    }
}