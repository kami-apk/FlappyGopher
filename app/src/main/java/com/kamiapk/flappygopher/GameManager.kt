package com.kamiapk.flappygopher

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.kamiapk.flappygopher.gopher.Gopher
import com.kamiapk.flappygopher.gopher.ObstaclesManager

/*
    スクリーン上の要素を制御するクラス
 */

class GameManager(context : Context, attributeSet : AttributeSet) : SurfaceView(context) , SurfaceHolder.Callback{



    init{
        //getHolderとaddCallbackを結びつける
        getHolder().addCallback(this)
    }

    //MainThreadのインスタンスを取得
    var thread = MainThread(getHolder(), this)

    //Gopherのインスタンス取得!
    private  var gopher : Gopher = Gopher(resources)

    //画面の幅を取得
    private val screenHeight = getScreenHeight()
    private val screenWidth = getScreenWidth()

    //Backgroundのインスタンス取得
    private val background = Background(resources, screenHeight)
    //障害物のインスタンス取得
    private val obstaclesManager = ObstaclesManager(resources, screenHeight, screenWidth )


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

    //Gopherクラスを利用してUIの変更
    fun update(){
        //Gopherクラスのupdate()を呼び出す
        gopher.update()
        //障害物を動かす
        obstaclesManager.update()
        //状態を変化させたら下のdrawメソッドが呼ばれて画面委描写される
    }

    //下で呼び出されるほど手前にくる
    override fun draw(canvas: Canvas){
        super.draw(canvas)
        canvas.drawRGB(150,255,255)
        background.draw(canvas)
        //画面にgopher君を呼び出すだけ
        //動作を付けるのはgopher.update()
        gopher.draw(canvas)
        obstaclesManager.draw(canvas)
    }

    //画面がタッチされた時のイベント
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gopher.onTouchEvent()
        return super.onTouchEvent(event)
    }

    //画面の高さを取得
    fun getScreenHeight() : Int {
        val dm = Resources.getSystem().displayMetrics
        return dm.heightPixels
    }

    fun getScreenWidth() : Int {
        val dm = Resources.getSystem().displayMetrics
        return dm.widthPixels
    }



}