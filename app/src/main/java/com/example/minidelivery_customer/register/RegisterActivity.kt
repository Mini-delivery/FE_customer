package com.example.minidelivery_customer.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.databinding.ActivityPaymentBinding
import com.example.minidelivery_customer.databinding.ActivityRegisterBinding
import com.example.minidelivery_customer.home.HomeActivity
import com.example.minidelivery_customer.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

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