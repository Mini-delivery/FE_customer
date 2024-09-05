package com.example.minidelivery_customer.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.home.HomeActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        setupRegisterButton()
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

}