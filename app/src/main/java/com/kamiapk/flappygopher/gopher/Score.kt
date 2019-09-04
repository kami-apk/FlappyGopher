package com.kamiapk.flappygopher.gopher

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.kamiapk.flappygopher.R

class Score(resources: Resources, screenHeight : Int, screenWidth : Int) : DisplayObject {

    private val zero = BitmapFactory.decodeResource(resources, R.drawable.zero)
    private val one = BitmapFactory.decodeResource(resources, R.drawable.one)
    private val two = BitmapFactory.decodeResource(resources, R.drawable.two)
    private val three = BitmapFactory.decodeResource(resources, R.drawable.three)
    private val four = BitmapFactory.decodeResource(resources, R.drawable.four)
    private val  five = BitmapFactory.decodeResource(resources, R.drawable.five)
    private val six = BitmapFactory.decodeResource(resources, R.drawable.six)
    private val seven = BitmapFactory.decodeResource(resources, R.drawable.seven)
    private val eight = BitmapFactory.decodeResource(resources, R.drawable.eight)
    private val nine = BitmapFactory.decodeResource(resources, R.drawable.nine)

    private val scoreBitmap = BitmapFactory.decodeResource(resources, R.drawable.score)
    private val bestBitmap = BitmapFactory.decodeResource(resources, R.drawable.best)


    private val screenHeight = screenHeight
    private val screenWidth = screenWidth

    private var score = 0
    private var best = 0

    //Bitmapと実数字の紐付け
    private var map = HashMap<Integer, Bitmap>()






    override fun draw(canvas: Canvas) {
    }

    override fun update() {
    }


}