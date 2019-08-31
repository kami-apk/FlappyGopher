package com.kamiapk.flappygopher

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class MainThread(surfaceHolder : SurfaceHolder , gameManager : GameManager) : Thread() {

    //引数を受け取る
    private var surfaceHolder = surfaceHolder
    private var gameManager = gameManager


    private  var running : Boolean = false
    private var canvas : Canvas? = Canvas()

    private val targetFPS : Int = 60

    private var flag = false

    override fun run() {
        var startTime : Long = 0L
        var timeMills : Long = 0L
        var waitTime : Long = 0L
        val targetTime :Long = (1000 / targetFPS).toLong()

        while(running){
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()
                //surfaceHolderで排他制御
                synchronized(surfaceHolder){
                    gameManager.update()
                    gameManager.draw(canvas!!)
                }

            } catch( e: Exception){
                e.printStackTrace()

            } finally{// 最後にかならず実行

                if( canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch( e: Exception){
                        e.printStackTrace()
                    }
                }

            }

            timeMills = (System.nanoTime() - startTime) / 1000 * 1000
            waitTime = targetTime - timeMills

            try {
                if(waitTime > 0){
                    sleep(waitTime)
                }
            } catch( e : Exception){
                e.printStackTrace()
            }


        }

    }


    fun setRunning(isRunning : Boolean) {
        running = isRunning
    }

}