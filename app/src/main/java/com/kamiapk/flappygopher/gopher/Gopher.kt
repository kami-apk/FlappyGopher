package com.kamiapk.flappygopher.gopher

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import com.kamiapk.flappygopher.GameManagerCallback
import com.kamiapk.flappygopher.R


class Gopher(resources : Resources, screenHeight : Int, callback : GameManagerCallback) : DisplayObject {

    //dpを扱うことで端末間による画面の大きさ問題を解決

    //画像の大きさを保持する変数をXMLファイルから読み込む
    //gopherの座標を示す
    private var gopherX = resources.getDimension(R.dimen.gopher_X)
    var gopherY = (screenHeight / 2).toFloat()
    //表示されるgopheerの大きさ
    private val gopherHeight : Int = resources.getDimension(R.dimen.gopher_height).toInt()
    private val gopherWidth : Int = resources.getDimension(R.dimen.gopher_width).toInt()

    private val screenHeight = screenHeight
    private val callback = callback

    //gopherの描写用
    private var i = 0

    //gopherの画像を端末に合わせて大きさ変更
    //yellow gopher
    val gopherBitmap_yellow : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.e)
    private val gopher_yellow = Bitmap.createScaledBitmap(gopherBitmap_yellow,gopherWidth,gopherHeight,false)
    //red gopher
    val gopherBitmap_red : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.redgopher)
    private val gopher_red = Bitmap.createScaledBitmap(gopherBitmap_red,gopherWidth,gopherHeight,false)
    //red gopher
    val gopherBitmap_t : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.a)
    private val gopher_t = Bitmap.createScaledBitmap(gopherBitmap_t,gopherWidth,gopherHeight,false)
    //墜落
    val gopherEnd: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.backgopher)
    private val gopher_end = Bitmap.createScaledBitmap(gopherEnd,gopherWidth,gopherHeight,false)

    //gopherの移動制限
    private val gravity : Float = resources.getDimension(R.dimen.gravity)
    private val tapBoost : Float = resources.getDimension(R.dimen.tap_boost)
    private var verocity : Float = 20F //初期速度
    //衝突ふらぐ
    var collision = false


    //GameManagerクラスから呼び出されUIを変更していく
    override fun draw(canvas: Canvas) {
        if(collision){
            canvas.drawBitmap(gopher_end, gopherX, gopherY, null)
        }else{
            if(i in 0..10){
                canvas.drawBitmap(gopher_red, gopherX, gopherY, null)
                i++
            }else if (i in 11..20){
                canvas.drawBitmap(gopher_yellow, gopherX, gopherY, null)
                i++
            }else{
                canvas.drawBitmap(gopher_t, gopherX, gopherY, null)
                i++
                if(i == 31){
                    i = 0
                }
            }
        }
    }

    override fun update() {
        if(collision){
            if(verocity<=0){
                verocity = 0F
            }
            if(gopherY + gopher_red.height < screenHeight){
                gopherY += verocity
                verocity += gravity * 0.1F
            }
        }else {
            gopherY += verocity
            verocity += gravity * 0.15F
            val gopherPosition = Rect(gopherX.toInt(), gopherY.toInt(),(gopherX + gopherWidth).toInt(), (gopherY + gopherHeight).toInt() )
            callback.upDatePosition(gopherPosition)
        }

    }

    fun onTouchEvent(){
        if(!collision){
            gopherY += tapBoost
            verocity = -15F
        }
    }

    fun collision(){
        collision = true
    }

}