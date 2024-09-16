package com.example.minidelivery_customer.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.adapter.GridRecyclerViewAdapter
import com.example.minidelivery_customer.adapter.ViewPagerAdapter
import com.example.minidelivery_customer.databinding.ActivityHomeBinding
import com.example.minidelivery_customer.item.BannerItem
import com.example.minidelivery_customer.item.FakeItem
import com.example.minidelivery_customer.item.GridItem
import com.example.minidelivery_customer.login.LoginActivity
import com.example.minidelivery_customer.model.Interaction
import com.example.minidelivery_customer.myPage.MyPageActivity
import com.example.minidelivery_customer.order.cafe.CafeActivity
import com.example.minidelivery_customer.order.chicken.ChickenActivity
import com.example.minidelivery_customer.order.korean.KoreanActivity
import com.example.minidelivery_customer.orderhistory.OrderHistoryActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), View.OnClickListener, Interaction {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewModel: HomeActivityViewModel
    private lateinit var gridRecyclerViewAdapter: GridRecyclerViewAdapter
    private var isRunning = true

    // FakeItem 인스턴스 생성
    private val fakeItem = FakeItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(HomeActivityViewModel::class.java)

        // FakeItem에서 가져온 데이터로 ViewModel 설정
        viewModel.setBannerItems(fakeItem.fakeBannerItemList)
        viewModel.setGridItems(fakeItem.fakeGridItemList)

        // drawerLayout 설정
        binding.ivHamburger.setOnClickListener(this)
        binding.llDrawer.llLeftArea.setOnClickListener(this)

        // 주문 내역 아이콘 클릭 리스너 설정 (툴바)
        binding.orderHistoryIcon.setOnClickListener {
            startOrderHistoryActivity()
        }

        // 슬라이드 메뉴의 주문 내역 클릭 리스너 설정
        binding.llDrawer.orderHistory.setOnClickListener {
            startOrderHistoryActivity()
        }

        initViewPager2()
        subscribeObservers()
        autoScrollViewPager()
        setupMyPageButton() // 마이페이지
        setupLogoutButton() // 로그아웃 기능
    }

    // 로그아웃 버튼
    private fun setupLogoutButton() {
        binding.llDrawer.logoutLayout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    // 로그아웃 확인 팝업창
    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("로그아웃")
            .setMessage("로그아웃 하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                logout()
            }
            .setNegativeButton("취소", null)
            .show()
    }

    // 로그아웃 실행
    private fun logout() {
        // SharedPreferences 데이터 삭제
        val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()

        // 로그인 액티비티로 이동
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // 마이페이지 액티비티 시작 함수
    private fun setupMyPageButton() {
        binding.llDrawer.myPage.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    // 주문 내역 액티비티 시작 함수
    private fun startOrderHistoryActivity() {
        val intent = Intent(this, OrderHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun initViewPager2() {
        // ViewPager2 초기화 및 설정
        binding.viewPager2.apply {
            viewPagerAdapter = ViewPagerAdapter(this@HomeActivity)
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    isRunning = true
                    binding.tvPageNumber.text = "${position + 1}"
                    viewModel.setCurrentPosition(position)
                }
            })
        }

        // GridRecyclerView 초기화 및 설정
        binding.gridRecyclerView.apply {
            gridRecyclerViewAdapter = GridRecyclerViewAdapter()
            layoutManager = GridLayoutManager(this@HomeActivity, 4)
            adapter = gridRecyclerViewAdapter

            // GridRecyclerViewAdapter에 클릭 리스너 설정
            gridRecyclerViewAdapter.setOnItemClickListener(object :
                GridRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(gridItem: GridItem) {
                    // 클릭된 아이템에 따라 해당 액티비티로 이동
                    when (gridItem.title) {
                        "카페·디저트" -> startActivity(Intent(this@HomeActivity, CafeActivity::class.java))
                        "한식" -> startActivity(Intent(this@HomeActivity, KoreanActivity::class.java))
                        "치킨" -> startActivity(Intent(this@HomeActivity, ChickenActivity::class.java))
                    }
                }
            })
        }
    }

    private fun subscribeObservers() {
        // ViewModel의 데이터 변경 관찰
        viewModel.bannerItemList.observe(this, Observer { bannerItemList ->
            viewPagerAdapter.submitList(bannerItemList)
        })
        viewModel.currentPosition.observe(this, Observer { currentPosition ->
            binding.viewPager2.currentItem = currentPosition
        })
        viewModel.gridItemList.observe(this, Observer { gridItemList ->
            gridRecyclerViewAdapter.submitList(gridItemList)
        })
    }

    private fun autoScrollViewPager() {
        // ViewPager2 자동 스크롤 기능
        lifecycleScope.launch {
            whenResumed {
                while (isRunning) {
                    delay(3000) // 3초 대기
                    viewModel.getcurrentPosition()?.let {
                        viewModel.setCurrentPosition((it.plus(1)) % 5)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isRunning = false // 자동 스크롤 중지
    }

    override fun onResume() {
        super.onResume()
        isRunning = true // 자동 스크롤 재개
    }

    override fun onBannerItemClicked(bannerItem: BannerItem) {
        // 배너 아이템 클릭 시 동작 구현 필요
    }

    override fun onClick(view: View?) {
        view?.let {
            when (it.id) {
                R.id.iv_hamburger -> {
                    // 햄버거 메뉴 아이콘 클릭 시 드로어 레이아웃 열기/닫기
                    val drawerLayout = binding.root.findViewById<DrawerLayout>(R.id.drawer_layout)
                    val llDrawer = binding.root.findViewById<View>(R.id.llDrawer)
                    if (drawerLayout.isDrawerOpen(llDrawer)) {
                        drawerLayout.closeDrawer(llDrawer)
                    } else {
                        drawerLayout.openDrawer(llDrawer)
                    }
                }
                R.id.ll_left_area -> {
                    // 드로어 레이아웃의 왼쪽 영역 클릭 시 드로어 닫기
                    val drawerLayout = binding.root.findViewById<DrawerLayout>(R.id.drawer_layout)
                    val llDrawer = binding.root.findViewById<View>(R.id.llDrawer)
                    if (drawerLayout.isDrawerOpen(llDrawer)) {
                        drawerLayout.closeDrawer(llDrawer)
                    }
                }
            }
        }
    }
}