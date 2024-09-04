package com.example.minidelivery_customer.model

import android.view.View
import com.example.minidelivery_customer.item.BannerItem

interface Interaction: View.OnClickListener {
    fun onBannerItemClicked(bannerItem: BannerItem)
}