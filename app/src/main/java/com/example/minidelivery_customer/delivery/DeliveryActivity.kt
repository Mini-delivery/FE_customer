package com.example.minidelivery_customer.delivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebSettings
import android.webkit.WebView
import com.google.android.material.tabs.TabLayout
import com.example.minidelivery_customer.R

class DeliveryActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var tabLayout: TabLayout

    // 액티비티 생성 시 호출되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery) // 레이아웃 설정

        setupTabLayout() // 탭 레이아웃 설정
        setupWebView() // WebView 설정
    }

    // 탭 레이아웃을 초기화하고 설정하는 함수
    private fun setupTabLayout() {
        tabLayout = findViewById(R.id.tabLayout) // 탭 레이아웃 뷰 찾기
        // 필요한 경우 여기에 탭 관련 추가 설정을 할 수 있습니다.
    }

    // WebView를 초기화하고 설정하는 함수
    private fun setupWebView() {
        webView = findViewById(R.id.webview) // WebView 찾기
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true // JavaScript 활성화

        // 스트리밍 URL 설정 (예: http://<your_ip>:8000/stream.mjpg)
        val streamingUrl = "http://192.168.137.141:5000" // 실제 스트리밍 URL을 여기에 입력하세요
        webView.loadUrl(streamingUrl) // URL 로드
    }
}