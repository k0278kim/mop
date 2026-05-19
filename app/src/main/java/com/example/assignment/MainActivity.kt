package com.example.assignment

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_launcher)
        title = "202235245 김태윤 12주차"

        setContentView(R.layout.activity_main)

        var btnFilelist: Button
        var edtFilelist: EditText
        btnFilelist = findViewById<Button>(R.id.btnFilelist)
        edtFilelist = findViewById<EditText>(R.id.edtFilelist)

        btnFilelist.setOnClickListener {
            var sysDir = Environment.getRootDirectory().absolutePath
            var sysFiles = File(sysDir).listFiles()

            var strFname: String
            for (i in sysFiles.indices) {
                if (sysFiles[i].isDirectory == true)
                    strFname = "<폴더> " + sysFiles[i].toString()
                else
                    strFname = "<파일> " + sysFiles[i].toString()
                edtFilelist.setText(edtFilelist.text.toString() + "\n" + strFname)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}