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

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    companion object {
        // Creates an Intent to start the LoginActivity.
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)


        setupSignInButton()
        setupSignUpButton()
    }

    private fun setupSignInButton() {
        val signInButton: Button = findViewById(R.id.signInButton)
        signInButton.setOnClickListener {
            val signinRequest = SigninRequest(loginId = emailEditText.text.toString(), password = passwordEditText.text.toString())

            RetrofitClient.instance.loginUser(signinRequest).enqueue(object : retrofit2.Callback<SigninResponse> {
                override fun onResponse(call: Call<SigninResponse>, response: retrofit2.Response<SigninResponse>) {
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

                override fun onFailure(call: Call<SigninResponse>, t: Throwable) {
                    // 네트워크 오류 처리
                    println("통신 실패: ${t.message}")
                }
            })

            navigateToHomeActivity()
        }
    }

    private fun setupSignUpButton() {
        val signUpButton: Button = findViewById(R.id.signUpButton)
        signUpButton.setOnClickListener {
            navigateToRegisterActivity()
        }
    }

    // Home Activity로 이동
    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Register Activity로 이동
    private fun navigateToRegisterActivity() {
        // Assuming you have a RegisterActivity. If not, you might want to create one or navigate to appropriate screen.
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        // Not finishing this activity as user might want to come back to login screen
    }
}