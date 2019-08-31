package com.kamiapk.flappygopher.gopher

import android.content.res.Resources
import android.graphics.*
import com.kamiapk.flappygopher.R
import kotlin.random.Random


class Obstacle(resources: Resources, screenHeight : Int,screenWidth : Int, callback : CallbackObstacle) : DisplayObject {



    private val images : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pipes)

    private val obstacleMiPosition = resources.getDimension(R.dimen.obstacle_min_position)

    //パイプのx座標を動かしていくことで障害を動かす
    var xPosition = screenWidth //初期値は画面の端からなのでscreenWidth
    private val screenHeight = screenHeight
    private val width = resources.getDimension(R.dimen.obstacle_width).toInt()
    //障害物の動くスピード
    private val speed = resources.getDimension(R.dimen.obstacle_speed).toInt()
    private val separation = resources.getDimension(R.dimen.obstacle_separation).toInt()
    private val headHeight = resources.getDimension(R.dimen.head_height).toInt()
    private val headExtraWidth = resources.getDimension(R.dimen.head_extra_width).toInt()
    private var height = (Random.nextInt((screenHeight - 2 * obstacleMiPosition - separation).toInt()) + obstacleMiPosition + 100).toInt()

    //private var i = 0
    private val callback = callback


    override fun draw(canvas: Canvas) {

        val bottomPipe = Rect(xPosition + headExtraWidth, screenHeight - height, xPosition + width + headExtraWidth, screenHeight)
        val bottomHead = Rect(xPosition, screenHeight - height - headHeight, xPosition + width + 2 * headExtraWidth, screenHeight - height)
        //大きい幅を用意
        val topPipe = Rect(xPosition + headExtraWidth, 0, xPosition + headExtraWidth + width, screenHeight - height - separation - 2 * headHeight)
        val topHead = Rect(xPosition, screenHeight - height - separation - 2 * headHeight, xPosition + width + 2 * headExtraWidth, screenHeight - height - separation - headHeight)

        var paint = Paint()

        canvas.drawBitmap(images, null, bottomPipe, paint)
        canvas.drawBitmap(images, null, bottomHead, paint)
        canvas.drawBitmap(images, null, topPipe, paint)
        canvas.drawBitmap(images, null, topHead, paint)

    }

    override fun update() {
        xPosition -= speed
        if( xPosition <= 0 - width - 2*headExtraWidth) {
            callback.removeObstacle(this)
        }
    }
}