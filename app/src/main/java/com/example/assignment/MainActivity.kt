package com.example.assignment

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.MultiAutoCompleteTextView
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
        title = "202235245 김태윤 8주차"
        setContentView(R.layout.activity_main)

        var items = arrayOf("CSI-뉴욕", "CSI-라스베가스", "CSI-마이애미", "Friends", "Fringe", "Lost")

        var auto = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView1)
        var adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        auto.setAdapter(adapter)

        var multi = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView1)
        var token = MultiAutoCompleteTextView.CommaTokenizer()
        multi.setTokenizer(token)
        multi.setAdapter(adapter)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}