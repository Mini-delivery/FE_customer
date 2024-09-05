package com.example.minidelivery_customer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.item.GridItem

class GridRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var gridItemList: List<GridItem>? = null

    // 클릭 리스너 인터페이스 정의
    interface OnItemClickListener {
        fun onItemClick(gridItem: GridItem)
    }

    // 클릭 리스너 변수 선언
    private var listener: OnItemClickListener? = null

    // 클릭 리스너 설정 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GridItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_grid, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return gridItemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        gridItemList?.let {
            (holder as GridItemViewHolder).bind(it[position])
        }
    }

    // 리스트 갱신 메서드
    fun submitList(list: List<GridItem>?) {
        gridItemList = list
        notifyDataSetChanged()
    }

    // ViewHolder 클래스
    inner class GridItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivGridImage: ImageView = itemView.findViewById(R.id.iv_grid_image)
        private val tvGridTitle: TextView = itemView.findViewById(R.id.tv_grid_title)

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

        fun bind(gridItem: GridItem) {
            ivGridImage.setImageResource(gridItem.image)
            tvGridTitle.text = gridItem.title
        }
    }
}