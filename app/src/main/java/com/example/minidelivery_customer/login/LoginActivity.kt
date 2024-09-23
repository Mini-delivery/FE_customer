package com.example.minidelivery_customer.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.api.RetrofitClient
import com.example.minidelivery_customer.api.SigninRequest
import com.example.minidelivery_customer.api.SigninResponse
import com.example.minidelivery_customer.home.HomeActivity
import com.example.minidelivery_customer.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        // 로그인 전 Retrofit 초기화 (토큰 없이)
        RetrofitClient.initRetrofitWithoutToken()

        setupSignInButton()
        setupSignUpButton()
    }

    private fun setupSignInButton() {
        val signInButton: Button = findViewById(R.id.signInButton)
        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val signinRequest = SigninRequest(loginId = email, password = password)

            // Retrofit을 사용하여 로그인 요청
            RetrofitClient.instance.loginUser(signinRequest).enqueue(object : Callback<SigninResponse> {
                override fun onResponse(call: Call<SigninResponse>, response: Response<SigninResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            if (it.success) {
                                Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()

                                val sharedPref = getSharedPreferences("loginData", MODE_PRIVATE)
                                val editor = sharedPref.edit()
                                editor.putString("loginId", it.loginId)
                                editor.putString("nickname", it.nickname)
                                editor.putString("address", it.address)
                                editor.putString("accessToken", it.accessToken)
                                editor.apply()
                                Log.d("UserData", "Response: ${response.body()?.nickname}, ${response.body()?.address}")

                                // 로그인 성공 후 토큰을 포함한 Retrofit 다시 초기화
                                RetrofitClient.initRetrofitWithToken(this@LoginActivity)

                                // 홈 화면으로 이동
                                navigateToHomeActivity()
                            } else {
                                Toast.makeText(this@LoginActivity, "로그인 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "서버 응답 오류: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SigninResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
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