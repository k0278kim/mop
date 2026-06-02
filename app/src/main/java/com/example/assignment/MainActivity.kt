package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_launcher)
        title = "202235245 김태윤 14주차"

        setContentView(R.layout.activity_main)

        var voteCount = IntArray(9) { 0 }
        var image = arrayOfNulls<ImageView>(9)
        var imageId = arrayOf(R.id.iv1, R.id.iv2, R.id.iv3,
            R.id.iv4, R.id.iv5, R.id.iv6,
            R.id.iv7, R.id.iv8, R.id.iv9)

        var imgName = arrayOf("독서하는 소녀", "꽃장식 모자 소녀",
            "부채를 든 소녀", "이레느깡 단 베르양",
            "잠자는 소녀", "테라스의 두 자매", "피아노 레슨",
            "피아노 앞의 소녀들", "해변에서")

        for (i in imageId.indices) {
            image[i] = findViewById<ImageView>(imageId[i])
            image[i]!!.setOnClickListener {
                voteCount[i]++
                Toast.makeText(applicationContext, imgName[i] + ": 총 " + voteCount[i] + " 표", Toast.LENGTH_SHORT).show()
            }
        }

        var btnFinish = findViewById<Button>(R.id.btnResult)
        btnFinish.setOnClickListener {
            var intent = Intent(applicationContext, ResultActivity::class.java)
            intent.putExtra("VoteCount", voteCount)
            intent.putExtra("ImageName", imgName)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}