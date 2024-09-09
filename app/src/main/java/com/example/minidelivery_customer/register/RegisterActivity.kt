package com.example.minidelivery_customer.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.api.ApiService
import com.example.minidelivery_customer.api.RetrofitClient
import com.example.minidelivery_customer.api.SignupRequest
import com.example.minidelivery_customer.api.SignupResponse
import com.example.minidelivery_customer.databinding.ActivityPaymentBinding
import com.example.minidelivery_customer.databinding.ActivityRegisterBinding
import com.example.minidelivery_customer.home.HomeActivity
import com.example.minidelivery_customer.login.LoginActivity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
  
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nickNameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        nickNameEditText = findViewById(R.id.nickName)


        setupRegisterButton()
        setupToolbar()
    }

    // Back Button : 이전화면으로 이동
    private fun setupToolbar() {
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRegisterButton() {
        val signInButton: Button = findViewById(R.id.registerButton)
        signInButton.setOnClickListener {
            val signupRequest = SignupRequest(loginId = emailEditText.text.toString(),
                password = passwordEditText.text.toString(),
                passwordCheck = passwordEditText.text.toString(),
                address = "서울특별시 성북구 삼선교로 7번길 한성대학교",
                nickname = nickNameEditText.text.toString())

            RetrofitClient.instance.registerUser(signupRequest).enqueue(object : retrofit2.Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse>, response: retrofit2.Response<SignupResponse>) {
                    if (response.isSuccessful) {
                        // 서버 응답 성공 처리
                        val result = response.body()
                        result?.let {
                            if (it.success) {
                                // 회원가입 성공
                                println("System: ${it.message}")
                            } else {
                                // 서버에서 회원가입 실패 처리
                                println("System: ${it.message}")
                            }
                        }
                    } else {
                        // 서버 오류 처리
                        println("서버 응답 오류: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    // 네트워크 오류 처리
                    println("통신 실패: ${t.message}")
                }
            })

            navigateToHomeActivity()
        }
    }

    // Home Activity로 이동
    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Login Activity로 이동
    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}