package com.example.minidelivery_customer.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minidelivery_customer.databinding.ActivityPaidBinding
import com.example.minidelivery_customer.home.HomeActivity

class PaidActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.button.setOnClickListener {
            // HomeActivity로 이동
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // 현재 액티비티를 종료
        }
    }
}