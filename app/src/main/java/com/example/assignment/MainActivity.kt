package com.example.assignment

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_launcher)
        title = "202235245 김태윤 14주차"

        val btnDial = findViewById<Button>(R.id.btnDial)
        val btnWeb = findViewById<Button>(R.id.btnWeb)
        val btnGoogle = findViewById<Button>(R.id.btnGoogle)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val btnSms = findViewById<Button>(R.id.btnSms)
        val btnPhoto = findViewById<Button>(R.id.btnPhoto)

        // 1. 전화 걸기 화면 열기
        btnDial.setOnClickListener {
            val uri = Uri.parse("tel:010-1234-5678")
            val intent = Intent(Intent.ACTION_DIAL, uri)
            startActivity(intent)
        }

        // 2. 웹 페이지 열기
        btnWeb.setOnClickListener {
            val uri = Uri.parse("https://www.google.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        // 3. 구글 지도 열기 (특정 위도, 경도 좌표 표시)
        btnGoogle.setOnClickListener {
            val uri = Uri.parse("https://maps.google.com/maps?q=37.5665,126.9780") // 서울시청 좌표 예시
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        // 4. 구글 웹 검색하기
        btnSearch.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, "안드로이드") // 검색어 입력
            startActivity(intent)
        }

        // 5. 문자 보내기 화면 열기
        btnSms.setOnClickListener {
            val uri = Uri.parse("smsto:010-1234-5678")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "안녕하세요?") // 미리 채워질 문자 내용
            startActivity(intent)
        }

        // 6. 카메라 열기 (사진 촬영)
        btnPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}