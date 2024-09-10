package com.example.minidelivery_customer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.item.BannerItem
import com.example.minidelivery_customer.model.Interaction

// ViewPager2에서 배너 아이템을 표시하기 위한 어댑터
// 생성자에서 Interaction 인터페이스를 받아 배너 클릭 이벤트를 처리
class ViewPagerAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ITEM_COUNT = 5 // 표시할 배너 아이템의 개수
    }

    private var bannerItemList: List<BannerItem>? = null // 배너 아이템 리스트

    // ViewHolder 생성
    // ViewPager2가 새로운 ViewHolder를 필요로 할 때 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BannerViewHolder( // BannerViewHolder 인스턴스
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_banner, parent, false),
            interaction
        )
    }

    // 전체 아이템 개수 반환
    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    // ViewHolder에 데이터 바인딩
    // 특정 위치의 데이터를 표시하기 위해 ViewHolder를 사용할 때 호출
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bannerItemList?.let { bannerItemList ->
            (holder as BannerViewHolder).bind(bannerItemList[position])
        }
    }

    // 배너 아이템 리스트 설정 및 갱신
    fun submitList(list: List<BannerItem>?) {
        bannerItemList = list
        notifyDataSetChanged()
    }

    // 배너 아이템을 표시하기 위한 ViewHolder
    class BannerViewHolder constructor(itemView: View, private val interaction: Interaction) :
        RecyclerView.ViewHolder(itemView) {
        private val ivBannerImage: ImageView = itemView.findViewById(R.id.iv_banner_image) // 배너 이미지 뷰

        // 배너 아이템 데이터 바인딩
        fun bind(bannerItem: BannerItem) {
            itemView.setOnClickListener {
                interaction.onBannerItemClicked(bannerItem) // 배너 클릭 이벤트 처리
            }
            ivBannerImage.setImageResource(bannerItem.image) // 배너 이미지 설정
        }
    }
}