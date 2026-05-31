package com.example.assignment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var ibZoomin: ImageButton
    lateinit var ibZoomout: ImageButton
    lateinit var ibRotate: ImageButton
    lateinit var ibBright: ImageButton
    lateinit var ibDark: ImageButton
    lateinit var ibGray: ImageButton
    lateinit var graphicView: MyGraphicView

    companion object {
        // 그래픽 변형을 위한 상태 변수들
        var sX = 1f          // 가로 배율
        var sY = 1f          // 세로 배율
        var angle = 0f       // 회전 각도
        var color = 1f       // 밝기 조절 배수 (1f가 원본)
        var satur = 1f       // 채도 조절 배수 (1f가 원본, 0f가 회색조)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_launcher)
        title = "202235245 김태윤 13주차"

        setContentView(R.layout.activity_main)

        val pictureLayout = findViewById<LinearLayout>(R.id.pictureLayout)
        graphicView = MyGraphicView(this)
        pictureLayout.addView(graphicView)

        clickIcons()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun clickIcons() {
        ibZoomin = findViewById(R.id.ibZoomin)
        ibZoomout = findViewById(R.id.ibZoomout)
        ibRotate = findViewById(
            resources.getIdentifier("ibRotate", "id", packageName).takeIf { it != 0 } ?: R.id.ibZoomin
            // id가 ibRotate가 맞는지 확인해 주세요. (틀릴 경우를 대비해 안전장치 가동)
        )
        // 만약 레이아웃 XML 파일의 ID가 다른 이름이라면 그에 맞게 아래 findViewById들을 연결해 주세요.
        ibRotate = findViewById(R.id.ibRotate)
        ibBright = findViewById(R.id.ibBright)
        ibDark = findViewById(R.id.ibDark)
        ibGray = findViewById(R.id.ibGray)

        // 1. 확대 버튼
        ibZoomin.setOnClickListener {
            sX += 0.2f
            sY += 0.2f
            graphicView.invalidate()
        }

        // 2. 축소 버튼
        ibZoomout.setOnClickListener {
            sX -= 0.2f
            sY -= 0.2f
            graphicView.invalidate()
        }

        // 3. 회전 버튼
        ibRotate.setOnClickListener {
            angle += 30f
            graphicView.invalidate()
        }

        // 4. 밝게 버튼
        ibBright.setOnClickListener {
            color += 0.2f
            graphicView.invalidate()
        }

        // 5. 어둡게 버튼
        ibDark.setOnClickListener {
            color -= 0.2f
            graphicView.invalidate()
        }

        // 6. 회색조 버튼 (토글 방식으로 작동하도록 구현)
        ibGray.setOnClickListener {
            satur = if (satur == 1f) 0f else 1f // 원래 색상 ↔ 회색조 전환
            graphicView.invalidate()
        }
    }

    class MyGraphicView(context: Context) : View(context) {

        // 비트맵은 생성자에서 한 번만 로드
        private val picture: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.lena256)
        private val paint = Paint()

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            // 화면 정중앙 좌표 계산
            val cenX = this.width / 2f
            val cenY = this.height / 2f

            // 1. 캔버스 변형 적용 (회전과 확대를 중앙 기준으로 처리)
            canvas.scale(sX, sY, cenX, cenY)
            canvas.rotate(angle, cenX, cenY)

            // 2. 밝기 및 회색조(채도) 조절을 위한 컬러 매트릭스 설정
            val cm = ColorMatrix()

            // 회색조(채도) 적용 (0f 이면 흑백, 1f 이면 컬러)
            cm.setSaturation(satur)

            // 밝기 조절 적용 (color 배열 요소를 곱해줌)
            if (color != 1f) {
                val array = floatArrayOf(
                    color, 0f, 0f, 0f, 0f,
                    0f, color, 0f, 0f, 0f,
                    0f, 0f, color, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
                cm.postConcat(ColorMatrix(array))
            }

            paint.colorFilter = ColorMatrixColorFilter(cm)

            // 3. 이미지가 정중앙에 오도록 그리기 시작할 좌표 계산
            val picX = (this.width - picture.width) / 2f
            val picY = (this.height - picture.height) / 2f

            // 최종 비트맵 그리기
            canvas.drawBitmap(picture, picX, picY, paint)
        }
    }
}