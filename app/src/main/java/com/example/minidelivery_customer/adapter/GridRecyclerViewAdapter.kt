package com.example.minidelivery_customer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.item.GridItem

// 그리드 형태의 RecyclerView를 위한 어댑터
class GridRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var gridItemList: List<GridItem>? = null // 그리드 아이템 리스트

    // 클릭 리스너 인터페이스 정의
    interface OnItemClickListener {
        fun onItemClick(gridItem: GridItem)
    }

    private var listener: OnItemClickListener? = null // 클릭 리스너 변수

    // 클릭 리스너 설정 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GridItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_grid, parent, false)
        )
    }

    // 전체 아이템 개수 반환
    override fun getItemCount(): Int {
        return gridItemList?.size ?: 0
    }

    // ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        gridItemList?.let {
            (holder as GridItemViewHolder).bind(it[position])
        }
    }

    // 그리드 아이템 리스트 설정 및 갱신
    fun submitList(list: List<GridItem>?) {
        gridItemList = list
        notifyDataSetChanged()
    }

    // 그리드 아이템을 표시하기 위한 ViewHolder
    inner class GridItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivGridImage: ImageView = itemView.findViewById(R.id.iv_grid_image) // 그리드 이미지 뷰
        private val tvGridTitle: TextView = itemView.findViewById(R.id.tv_grid_title) // 그리드 제목 텍스트 뷰

        init {
            // 아이템 클릭 리스너 설정
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    gridItemList?.get(position)?.let { item ->
                        listener?.onItemClick(item)
                    }
                }
            }
        }

        // 그리드 아이템 데이터 바인딩
        fun bind(gridItem: GridItem) {
            ivGridImage.setImageResource(gridItem.image) // 그리드 이미지 설정
            tvGridTitle.text = gridItem.title // 그리드 제목 설정
        }
    }
}