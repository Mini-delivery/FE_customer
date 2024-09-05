package com.example.minidelivery_customer.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.R
import com.example.minidelivery_customer.home.HomeActivity
import com.example.minidelivery_customer.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    companion object {
        // Creates an Intent to start the LoginActivity.
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupSignInButton()
        setupSignUpButton()
    }

    private fun setupSignInButton() {
        val signInButton: Button = findViewById(R.id.signInButton)
        signInButton.setOnClickListener {
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