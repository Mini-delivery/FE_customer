package com.example.minidelivery_customer.item

import com.example.minidelivery_customer.R

class FakeItem {
    val fakeBannerItemList = listOf(
        BannerItem(R.drawable.banner1),
        BannerItem(R.drawable.banner2),
        BannerItem(R.drawable.banner3),
        BannerItem(R.drawable.banner4),
        BannerItem(R.drawable.banner5)
    )

    val fakeGridItemList = listOf(
        GridItem(R.drawable.a, "1인분"),
        GridItem(R.drawable.d, "한식"),
        GridItem(R.drawable.e, "분식"),
        GridItem(R.drawable.f, "카페·디저트"),
        GridItem(R.drawable.g, "돈까스·회·일식"),
        GridItem(R.drawable.h, "치킨"),
        GridItem(R.drawable.i, "피자"),
        GridItem(R.drawable.j, "이시안·양식"),
        GridItem(R.drawable.k, "중국집"),
        GridItem(R.drawable.l, "족발·보쌈"),
        GridItem(R.drawable.m, "야식"),
        GridItem(R.drawable.n, "찜·탕"),
        GridItem(R.drawable.o, "도시락"),
        GridItem(R.drawable.p, "패스트푸드"),
        GridItem(R.drawable.q, "프랜차이즈"),
        GridItem(R.drawable.s, "맛집랭킹"),
    )
}