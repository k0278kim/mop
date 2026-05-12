package com.example.assignment

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var baseLayout: RelativeLayout
    lateinit var et: EditText
    lateinit var iv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_launcher)
        title = "202235245 김태윤 11주차"

        setContentView(R.layout.activity_main)

        baseLayout = findViewById<RelativeLayout>(R.id.baseLayout)
        et = findViewById<EditText>(R.id.et)
        iv = findViewById<ImageView>(R.id.iv)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.baseLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        var mInflater = menuInflater
        mInflater.inflate(R.menu.menu1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.rotate -> {
                iv.rotation = et.text.toString().toFloat()
                return true
            }
            R.id.item1 -> {
                iv.setImageResource(R.drawable.halla)
                item.isChecked = true
                return true
            }
            R.id.item2 -> {
                iv.setImageResource(R.drawable.chooja)
                item.isChecked = true
                return true
            }
            R.id.item3 -> {
                iv.setImageResource(R.drawable.beom)
                item.isChecked = true
                return true
            }
        }
        return false
    }
}