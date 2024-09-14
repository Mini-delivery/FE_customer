package com.example.minidelivery_customer.payment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.api.OrderRequest
import com.example.minidelivery_customer.api.RetrofitClient
import com.example.minidelivery_customer.databinding.ActivityPaymentBinding
import com.example.minidelivery_customer.order.CartItem
import com.example.minidelivery_customer.order.OrderItem
import com.example.minidelivery_customer.orderhistory.OrderHistoryItem
import com.example.minidelivery_customer.orderhistory.OrderHistoryUtil
import com.google.gson.Gson
import java.text.NumberFormat
import java.util.*
import retrofit2.*

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding // 뷰 바인딩 객체
    private lateinit var orderItem: OrderItem // 주문 정보 객체
    private lateinit var cartItems: List<CartItem> // 장바구니 아이템 리스트
    private lateinit var addressView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater) // 뷰 바인딩 초기화
        setContentView(binding.root) // 레이아웃 설정
        // OrderItem 객체 가져오기 (Android 버전에 따라 다른 방식 사용)
        orderItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("orderItem", OrderItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("orderItem") as? OrderItem
        } ?: run {
            // 주문 정보를 가져오지 못했을 경우 처리
            Toast.makeText(this, "주문 정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        cartItems = getCartItems() // 장바구니 아이템 가져오기
        setupToolbar() // 툴바 설정
        setupStoreInfo() // 가게 정보 설정
        setupCartItems() // 장바구니 아이템 설정
        setupPaymentButton() // 결제 버튼 설정
        setupHomeAddress() // 사용자 주소 설정
    }

    private fun setupToolbar() {
        // 뒤로 가기 버튼 클릭 리스너 설정
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupStoreInfo() {
        binding.storeTextView.text = orderItem.name // 가게 이름 설정
        binding.store.text = orderItem.address // 가게 주소 설정
    }

    private fun setupCartItems() {
        // RecyclerView 설정
        binding.cartItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PaymentActivity)
            adapter = CartAdapter(cartItems)
        }

        // 총 가격 계산
        val totalPrice = cartItems.sumBy {
            it.price.replace("[^0-9]".toRegex(), "").toInt() * it.quantity
        }
        // 가격 포맷팅
        val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(totalPrice)
        // 메뉴 가격과 총 가격 표시
        binding.menuPrice.text = "${formattedPrice}원"
        binding.totalPrice.text = "${formattedPrice}원"
    }

    private fun setupPaymentButton() {
        // 결제 버튼 클릭 리스너 설정
        binding.paymentButton.setOnClickListener {
            val intent = Intent(this, PaidActivity::class.java)
            intent.putExtra("orderItem", orderItem) // 주문 정보 전달
            intent.putExtra("cartItems", ArrayList(cartItems)) // 장바구니 아이템 전달
            sendOrderToServer()
            startActivity(intent) // PaidActivity 시작
            finish() // 현재 액티비티 종료
        }
    }

    private fun setupHomeAddress() {
        val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val userAddress = sharedPref.getString("userAddress", "주소 정보 없음")
        binding.address.text = userAddress
    }

    private fun getCartItems(): List<CartItem> {
        // Android 버전에 따라 다른 방식으로 장바구니 아이템 가져오기
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("cartItems", CartItem::class.java) ?: emptyList()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra<CartItem>("cartItems") ?: emptyList()
        }
    }

    private fun sendOrderToServer() {
        var orderName = cartItems.joinToString(", ") { it.name }
        val sharedPref = getSharedPreferences("loginData", MODE_PRIVATE)
        val nickname = sharedPref.getString("nickname", null)

        val orderRequest = nickname?.let {
            OrderRequest(
                user_id = it,
                order_name = orderName,
                price = cartItems.sumOf {
                    it.price.replace("[^0-9]".toRegex(), "").toInt() * it.quantity
                },  // 총 가격 계산
                address = orderItem.address,  // 주문한 주소
                store_id = orderItem.name    // 가게 이름
            )
        }

        // JSON 로그로 출력
        val gson = Gson()
        Log.d("OrderRequest", "Sending order request: ${gson.toJson(orderRequest)}")

        if (orderRequest != null) {
            // 서버로 주문 정보 전송
            val apiService = RetrofitClient.instance
            val call = apiService.sendOrder(orderRequest)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d("PaymentActivity", "Response code: ${response.code()}")  // 응답 코드 로그 추가
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@PaymentActivity,
                            "주문이 성공적으로 전송되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        // 결제 완료 후 화면 이동
                        val intent = Intent(this@PaymentActivity, PaidActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("PaymentActivity", "Error response: ${response.errorBody()?.string()}")  // 에러 응답 로그 추가
                        Toast.makeText(this@PaymentActivity, "서버 오류: 주문 전송 실패", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("PaymentActivity", "Network failure: ${t.message}", t)  // 네트워크 오류 로그 추가
                    Toast.makeText(this@PaymentActivity, "네트워크 오류: 주문 전송 실패", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } else {
            Toast.makeText(this, "사용자 닉네임을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }


}