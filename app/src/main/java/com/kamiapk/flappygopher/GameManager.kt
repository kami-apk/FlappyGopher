package com.kamiapk.flappygopher

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

/*
スクリーン上の要素を制御するクラス
 */

class GameManager(context : Context, attributeSet : AttributeSet) : SurfaceView(context) , SurfaceHolder.Callback{

    init{
        //initでgetHolderとaddCallbackを結びつける
        getHolder().addCallback(this)
    }

    var thread = MainThread(getHolder(), this)



    override fun surfaceCreated(p0: SurfaceHolder?) {

        //アプリをいったんバックグラウンドにしてしまったときに戻ると落ちるのでその対策
        if(thread.state == Thread.State.TERMINATED){
            thread = MainThread(getHolder(), this)
        }

        thread.setRunning(true)
        thread.start()
    }




    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        var retry : Boolean = true
        while(retry){
            try{
                thread.setRunning(false)
                thread.join()
            } catch (e : InterruptedException){
               e.printStackTrace()
            }
            retry = false
        }
    }

    fun update(){
        Log.w("TTT","Gamemanager update call")
    }

    override fun draw(canvas: Canvas){
        super.draw(canvas)
    }




}