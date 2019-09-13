package com.kamiapk.flappygopher

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import com.kamiapk.flappygopher.gopher.BackgroundState
import com.kamiapk.flappygopher.gopher.DisplayObject




class Background(resources : Resources, screenHeight : Int, private val screenWidth: Int) : DisplayObject {

    //初期状態
    private var backgroundState = BackgroundState.FIRST

    //画像の位置
    private var firstPosition : Int = 0
    private var secondPosition : Int  = screenWidth

    //画像リソースからビットマップとして呼び出す
    private val backgroundBitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ahoy)

    //端末に合わせてBitmapの大きさを変える
    private val bgbFirst = Bitmap.createScaledBitmap(backgroundBitmap,backgroundBitmap.width, screenHeight, false)
    private val bgbSecond = Bitmap.createScaledBitmap(backgroundBitmap,backgroundBitmap.width, screenHeight, false)


    //backgroundにBitmapを表示させる
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bgbFirst,firstPosition.toFloat(),0F,null)
        canvas.drawBitmap(bgbSecond,secondPosition.toFloat(),0F,null)
    }

    //画面描写のロジック
    override fun update() {
        when ( backgroundState ) {

            BackgroundState.FIRST -> {
                firstPosition--
                if( -firstPosition + screenWidth == bgbFirst.width){
                    secondPosition--
                    backgroundState = BackgroundState.BOTH
                }
            }

            BackgroundState.BOTH -> {
                firstPosition--
                secondPosition--
                if( -firstPosition == bgbFirst.width){
                    firstPosition = screenWidth
                    backgroundState = BackgroundState.SECOND
                }

            }

            BackgroundState.SECOND -> {
                secondPosition--
                if (-secondPosition + screenWidth == bgbFirst.width){
                    firstPosition--
                    backgroundState = BackgroundState.LAST
                }
            }

            BackgroundState.LAST -> {
                firstPosition--
                secondPosition--
                if( -secondPosition == bgbFirst.width){
                    secondPosition = screenWidth
                    backgroundState = BackgroundState.FIRST
                }


            }


        }


    }

}

