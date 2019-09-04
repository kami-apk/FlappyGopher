package com.kamiapk.flappygopher.gopher

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.kamiapk.flappygopher.R

class GameOver(resources: Resources,screenHeight : Int, screenWidth : Int) : DisplayObject {

    private val gameOver : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.gameover)

    private val screenHeight = screenHeight
    private val screenWidth = screenWidth



    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(gameOver,(screenWidth / 2 - gameOver.width/2).toFloat() , (screenHeight /4 ).toFloat(), null)
    }

    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}