package com.kamiapk.flappygopher

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.kamiapk.flappygopher.gopher.DisplayObject

class GameMessage(resources: Resources, screenHeight : Int, screenWidth : Int) : DisplayObject {

    private val screenHeight = screenHeight
    private val screeningWidth = screenWidth

    private val titleScreen = BitmapFactory.decodeResource(resources,R.drawable.message)
    private val gopher = BitmapFactory.decodeResource(resources,R.drawable.gopher)


    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(gopher,(screeningWidth - gopher.width).toFloat(),(screenHeight / 2).toFloat(),null)
        canvas.drawBitmap(titleScreen,(screeningWidth / 2 - titleScreen.width / 2).toFloat() , (screenHeight / 4).toFloat(), null)
    }

    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }





}