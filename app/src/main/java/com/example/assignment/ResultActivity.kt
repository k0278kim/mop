package com.example.assignment

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result)
        title = "투표 결과"

        var intent = intent
        var voteResult = intent.getIntArrayExtra("VoteCount")
        var imageName = intent.getStringArrayExtra("ImageName")

        val imageField = arrayOf(
            R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
            R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
            R.drawable.pic7, R.drawable.pic8, R.drawable.pic9
        )

        var maxIndex = 0
        for (i in 1 until voteResult!!.size) {
            if (voteResult[i] > voteResult[maxIndex]) {
                maxIndex = i
            }
        }

        val tvTop = findViewById<TextView>(R.id.tvTop)
        val ivTop = findViewById<ImageView>(R.id.ivTop)
        tvTop.text = imageName!![maxIndex]
        ivTop.setImageResource(imageField[maxIndex])

        var tvIDs = arrayOf(R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9)
        var rbarIDs = arrayOf(R.id.rbar1, R.id.rbar2, R.id.rbar3, R.id.rbar4, R.id.rbar5, R.id.rbar6, R.id.rbar7, R.id.rbar8, R.id.rbar9)

        for (i in voteResult.indices) {
            val tv = findViewById<TextView>(tvIDs[i])
            val rbar = findViewById<RatingBar>(rbarIDs[i])
            tv.text = imageName[i]
            rbar.rating = voteResult[i].toFloat()
        }

        var btnReturn = findViewById<Button>(R.id.btnReturn)
        btnReturn.setOnClickListener {
            finish()
        }
    }
}