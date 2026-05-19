package com.example.assignment

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_launcher)
        title = "202235245 김태윤 12주차"

        setContentView(R.layout.activity_main)

        var btnRead: Button = findViewById<Button>(R.id.btnRead)
        var btnWrite: Button = findViewById<Button>(R.id.btnWrite)

        btnWrite.setOnClickListener {
            var outFs: FileOutputStream = openFileOutput("file.txt", Context.MODE_PRIVATE)
            var str = "쿡북 안드로이드"
            outFs.write(str.toByteArray())
            outFs.close()
            Toast.makeText(applicationContext, "file.txt가 생성됨", Toast.LENGTH_SHORT).show()
        }

        btnRead.setOnClickListener {
            try {
                var inFs: FileInputStream = openFileInput("file.txt")
                var txt = ByteArray(30)
                inFs.read(txt)
                var str = txt.toString(Charsets.UTF_8)
                Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
                inFs.close()
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "파일 없음", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}