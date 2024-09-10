package com.example.minidelivery_customer.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.api.RetrofitClient
import com.example.minidelivery_customer.api.SigninRequest
import com.example.minidelivery_customer.api.SigninResponse
import com.example.minidelivery_customer.api.SignupRequest
import com.example.minidelivery_customer.api.SignupResponse
import com.example.minidelivery_customer.home.HomeActivity
import com.example.minidelivery_customer.register.RegisterActivity
import retrofit2.Call

// 로그인 화면을 관리하는 액티비티
class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText // 이메일 입력 필드
    private lateinit var passwordEditText: EditText // 비밀번호 입력 필드

    companion object {
        // LoginActivity를 시작하는 Intent를 생성하는 함수
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // 레이아웃 설정

        // 뷰 초기화
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        setupSignInButton() // 로그인 버튼 설정
        setupSignUpButton() // 회원가입 버튼 설정
    }

    // 로그인 버튼 설정 및 동작 정의
    private fun setupSignInButton() {
        val signInButton: Button = findViewById(R.id.signInButton)
        signInButton.setOnClickListener {
            // 로그인 요청 객체 생성
            val signinRequest = SigninRequest(loginId = emailEditText.text.toString(), password = passwordEditText.text.toString())

            // Retrofit을 사용하여 서버에 로그인 요청
            RetrofitClient.instance.loginUser(signinRequest).enqueue(object : retrofit2.Callback<SigninResponse> {
                // 서버 응답 처리
                override fun onResponse(call: Call<SigninResponse>, response: retrofit2.Response<SigninResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            if (it.success) {
                                println("System: ${it.message}") // 로그인 성공 메시지 출력
                            } else {
                                println("System: ${it.message}") // 로그인 실패 메시지 출력
                            }
                        }
                    } else {
                        println("서버 응답 오류: ${response.code()}") // 서버 오류 처리
                    }
                }

                // 네트워크 오류 처리
                override fun onFailure(call: Call<SigninResponse>, t: Throwable) {
                    println("통신 실패: ${t.message}")
                }
            })

            navigateToHomeActivity() // 홈 화면으로 이동
        }
    }

    // 회원가입 버튼 설정 및 동작 정의
    private fun setupSignUpButton() {
        val signUpButton: Button = findViewById(R.id.signUpButton)
        signUpButton.setOnClickListener {
            navigateToRegisterActivity() // 회원가입 화면으로 이동
        }
    }

    // 홈 화면으로 이동하는 함수
    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // 현재 액티비티 종료
    }

    // 회원가입 화면으로 이동하는 함수
    private fun navigateToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        // 현재 액티비티를 종료하지 않음 (사용자가 로그인 화면으로 돌아올 수 있도록)
    }
}