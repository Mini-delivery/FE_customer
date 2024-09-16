package com.example.minidelivery_customer.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.api.RetrofitClient
import com.example.minidelivery_customer.api.SignupRequest
import com.example.minidelivery_customer.api.SignupResponse
import com.example.minidelivery_customer.databinding.ActivityRegisterBinding
import com.example.minidelivery_customer.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding // 뷰 바인딩 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater) // 뷰 바인딩 초기화
        setContentView(binding.root) // 레이아웃 설정

        setupToolbar() // 툴바 설정
        setupRegisterButton() // 회원가입 버튼 설정
    }

    // 툴바 설정 (뒤로 가기 버튼)
    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressed() // 뒤로 가기 기능 실행
        }
    }

    // 회원가입 버튼 설정 및 기능 구현
    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            // 입력 필드에서 데이터 가져오기
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val passwordCheck = binding.passwordCheck.text.toString()
            val address = binding.address.text.toString()
            val nickname = binding.nickname.text.toString()

            // 이메일 유효성 검사
            // 조건: 이메일 형식(@와 . 포함)
            if (!isValidEmail(email)) {
                Toast.makeText(this, "이메일 형식에 맞지 않습니다. 이메일을 수정해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 유효성 검사
            // 조건: 4글자 이상
            if (password.length < 4) {
                Toast.makeText(this, "비밀번호는 4글자 이상이어야합니다. 수정해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 확인 체크
            // 조건: password와 passwordCheck가 일치해야 함
            if (password != passwordCheck) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다. 수정해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 회원가입 성공 시 주소 정보 저장
            val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("userAddress", address)
                apply()
            }

            // 회원가입 요청 객체 생성
            val signupRequest = SignupRequest(loginId = email, password = password, passwordCheck = passwordCheck, address = address, nickname = nickname)

            // Retrofit을 사용하여 서버에 회원가입 요청
            RetrofitClient.instance.registerUser(signupRequest).enqueue(object : Callback<SignupResponse> {
                // 서버 응답 처리
                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            if (it.success) {
                                Toast.makeText(this@RegisterActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                navigateToHomeActivity() // 홈 화면으로 이동
                            } else {
                                Toast.makeText(this@RegisterActivity, it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@RegisterActivity, "서버 오류: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                // 네트워크 오류 처리
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "통신 실패: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    // 이메일 유효성 검사 함수
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() // 안드로이드에서 제공하는 이메일 패턴 검사 사용
    }

    // 홈 화면으로 이동하는 함수
    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // 현재 액티비티 종료
    }
}