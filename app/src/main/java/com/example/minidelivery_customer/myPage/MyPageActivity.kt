package com.example.minidelivery_customer.myPage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.api.RetrofitClient
import com.example.minidelivery_customer.api.UpdateUserRequest
import com.example.minidelivery_customer.api.UpdateUserResponse
import com.example.minidelivery_customer.api.UserInfoResponse
import com.example.minidelivery_customer.databinding.ActivityMyPageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupUpdateButton()
        loadUserInfo()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadUserInfo() {
        RetrofitClient.instance.getUserInfo().enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        if (it.success && it.data != null) {
                            binding.nicknameEditText.setText(it.data.nickname)
                            binding.addressEditText.setText(it.data.address)
                            binding.emailTextView.text = it.data.email // 이메일은 TextView에 표시
                        } else {
                            Toast.makeText(this@MyPageActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@MyPageActivity, "사용자 정보 로드 실패: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Toast.makeText(this@MyPageActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupUpdateButton() {
        binding.updateButton.setOnClickListener {
            val nickname = binding.nicknameEditText.text.toString()
            val address = binding.addressEditText.text.toString()

            if (nickname.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updateRequest = UpdateUserRequest(nickname = nickname, address = address)

            RetrofitClient.instance.updateUser(updateRequest).enqueue(object : Callback<UpdateUserResponse> {
                override fun onResponse(call: Call<UpdateUserResponse>, response: Response<UpdateUserResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            if (it.success) {
                                Toast.makeText(this@MyPageActivity, "프로필 업데이트 성공", Toast.LENGTH_SHORT).show()
                                loadUserInfo() // 업데이트 후 최신 정보를 다시 로드
                            } else {
                                Toast.makeText(this@MyPageActivity, it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@MyPageActivity, "서버 오류: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                    Toast.makeText(this@MyPageActivity, "통신 실패: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}