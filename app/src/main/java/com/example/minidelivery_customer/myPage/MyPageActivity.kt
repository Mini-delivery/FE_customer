package com.example.minidelivery_customer.myPage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.api.RetrofitClient
import com.example.minidelivery_customer.api.UpdateUserRequest
import com.example.minidelivery_customer.api.UpdateUserResponse
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
        loadUserInfoFromSharedPreferences() // 서버 대신 SharedPreferences에서 정보 로드
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // 서버가 아닌 SharedPreferences에서 사용자 정보 로드
    private fun loadUserInfoFromSharedPreferences() {
        val sharedPref = getSharedPreferences("loginData", MODE_PRIVATE)

        val nickname = sharedPref.getString("nickname", null)
        val address = sharedPref.getString("address", null)
        val email = sharedPref.getString("loginId", null)

        // 사용자 정보가 있으면 화면에 설정
        binding.nicknameEditText.setText(nickname)
        binding.addressEditText.setText(address)
        binding.emailTextView.text = email // 이메일은 TextView에 표시
    }

    // 사용자 정보 업데이트 버튼 설정
    private fun setupUpdateButton() {
        binding.updateButton.setOnClickListener {
            val nickname = binding.nicknameEditText.text.toString()
            val address = binding.addressEditText.text.toString()

            if (nickname.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updateRequest = UpdateUserRequest(nickname = nickname, address = address)

            // 서버로 업데이트 요청
            RetrofitClient.instance.updateUser(updateRequest).enqueue(object : Callback<UpdateUserResponse> {
                override fun onResponse(call: Call<UpdateUserResponse>, response: Response<UpdateUserResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            if (it.success) {
                                Toast.makeText(this@MyPageActivity, "프로필 업데이트 성공", Toast.LENGTH_SHORT).show()

                                // SharedPreferences에 업데이트된 사용자 정보 저장
                                val sharedPref = getSharedPreferences("loginData", MODE_PRIVATE)
                                val editor = sharedPref.edit()

                                editor.putString("nickname", nickname) // 변경된 닉네임 저장
                                editor.putString("address", address)   // 변경된 주소 저장
                                editor.apply()

                                // 정보 갱신 후 화면 업데이트
                                loadUserInfoFromSharedPreferences()
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
