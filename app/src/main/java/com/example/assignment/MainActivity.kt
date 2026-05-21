package com.example.assignment

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.drawToBitmap
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var btnPrev: Button
    lateinit var btnNext: Button
    lateinit var myPicture: myPictureView

    private var curNum: Int = 0
    private var imageUris: MutableList<Uri> = mutableListOf()

    private var REQUEST_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_launcher)
        title = "202235245 김태윤 12주차"

        setContentView(R.layout.activity_main)

        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        myPicture = findViewById(R.id.myPictureView1)

        checkPermissionAndLoadImages()

        btnPrev.setOnClickListener {
            if (curNum <= 0) {
                Toast.makeText(this, "첫 번째 이미지입니다", Toast.LENGTH_SHORT).show()
            } else {
                curNum--
                displayImage(curNum)
            }
        }

        btnNext.setOnClickListener {
            if (curNum >= imageUris.size - 1) {
                Toast.makeText(this, "마지막 이미지입니다", Toast.LENGTH_SHORT).show()
            } else {
                curNum++
                displayImage(curNum)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkPermissionAndLoadImages() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_PERMISSION)
        } else {
            scanPicturesFolder()
            loadImagesFromMediaStore()
        }
    }

    private fun scanPicturesFolder() {
        val folderPath = "/storage/emulated/0/Pictures"

        val filePaths = Array(10) { i -> "$folderPath/${i + 1}.png" }
        val mimeTypes = Array(10) { "image/png" }

        MediaScannerConnection.scanFile(
            this,
            filePaths,
            mimeTypes,
            null
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            scanPicturesFolder()
            loadImagesFromMediaStore()
        } else {
            Toast.makeText(this, "권한이 필요합니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImagesFromMediaStore() {
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val selection = "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("%Pictures%")
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { it ->
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                imageUris.add(contentUri)
            }
        }

        if (imageUris.isNotEmpty()) {
            curNum = 0
            displayImage(curNum)
        } else {
            Toast.makeText(this, "이미지를 찾을 수 없습니다", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayImage(index: Int) {
        val uri = imageUris[index]
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        myPicture.bitmap = bitmap
        myPicture.invalidate() // 비어있던 공간: 다시 그리기 요청
    }
}