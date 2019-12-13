package com.kamiapk.flappygopher

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.kamiapk.flappygopher.gopher.DisplayObject

class GameMessage(resources: Resources, screenHeight : Int, screenWidth : Int) : DisplayObject {

    private val screenHeight = screenHeight
    private val screeningWidth = screenWidth

    private val titleScreen = BitmapFactory.decodeResource(resources,R.drawable.message)
    private val gopher = BitmapFactory.decodeResource(resources,R.drawable.gopher)

    private val gopherBit = Bitmap.createScaledBitmap(gopher,screenWidth,gopher.height*screenWidth/gopher.width,false)


    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(gopherBit,0f,(screenHeight / 2).toFloat(),null)
        canvas.drawBitmap(titleScreen,(screeningWidth / 2 - titleScreen.width / 2).toFloat() , (screenHeight / 4).toFloat(), null)
    }

    override fun update() {
    }


}