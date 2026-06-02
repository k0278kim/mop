package com.example.assignment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second)
        title = "Second 액티비티"

        val num1 = intent.getIntExtra("Num1", 0)
        val num2 = intent.getIntExtra("Num2", 0)
        val type = intent.getIntExtra("Type", 0)

        var result = 0

        when (type) {
            R.id.rd_add -> result = num1 + num2
            R.id.rd_sub -> result = num1 - num2
            R.id.rd_mul -> result = num1 * num2
            R.id.rd_div -> {
                if (num2 != 0) {
                    result = num1 / num2
                }
            }
        }

        val btnReturn = findViewById<Button>(R.id.btnReturn)
        btnReturn.setOnClickListener {
            val outIntent = Intent(applicationContext, MainActivity::class.java)
            outIntent.putExtra("Result", result)
            setResult(Activity.RESULT_OK, outIntent)
            finish()
        }
    }
}