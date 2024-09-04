package com.example.minidelivery_customer.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minidelivery_customer.item.BannerItem
import com.example.minidelivery_customer.item.GridItem

class HomeActivityViewModel : ViewModel() {
    // 배너 아이템 리스트를 저장하는 private MutableLiveData
    private val _bannerItemList: MutableLiveData<List<BannerItem>> = MutableLiveData()
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()
    private val _gridItemList: MutableLiveData<List<GridItem>> = MutableLiveData()

    // 외부에서 관찰 가능한 읽기 전용 LiveData
    val bannerItemList: LiveData<List<BannerItem>>
        get() = _bannerItemList
    val currentPosition: LiveData<Int>
        get() = _currentPosition
    val gridItemList: LiveData<List<GridItem>>
        get() = _gridItemList

    init{
        _currentPosition.value=0
    }

    // 배너 아이템 리스트를 설정하는 함수
    fun setBannerItems(list: List<BannerItem>){
        _bannerItemList.value = list
    }

    fun setCurrentPosition(position: Int){
        _currentPosition.value = position
    }

    fun getcurrentPosition() = currentPosition.value

    // Grid Item
    fun setGridItems(list: List<GridItem>) {
        _gridItemList.value = list
    }
}