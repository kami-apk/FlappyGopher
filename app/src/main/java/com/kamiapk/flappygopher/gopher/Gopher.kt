package com.kamiapk.flappygopher.gopher

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.kamiapk.flappygopher.R


class Gopher(resources : Resources) : DisplayObject {

    //dpを扱うことで端末間による画面の大きさ問題を解決

    //画像の大きさを保持する変数をXMLファイルから読み込む
    //gopherの座標を示す
    private var gopherX = resources.getDimension(R.dimen.gopher_X)
    private var gopherY = resources.getDimension(R.dimen.gopher_Y)
    //表示されるgopheerの大きさ
    private val gopherHeight : Int = resources.getDimension(R.dimen.gopher_height).toInt()
    private val gopherWidth : Int = resources.getDimension(R.dimen.gopher_width).toInt()

    //gopherの画像を端末に合わせて大きさ変更
    //yellow gopher
    val gopherBitmap_yellow : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.e)
    private val gopher_yellow = Bitmap.createScaledBitmap(gopherBitmap_yellow,gopherWidth,gopherHeight,false)
    //red gopher
    val gopherBitmap_red : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.redgopher)
    private val gopher_red = Bitmap.createScaledBitmap(gopherBitmap_red,gopherWidth,gopherHeight,false)

    //gopherの移動制限
    private val gravity : Float = resources.getDimension(R.dimen.gravity)
    private val tapBoost : Float = resources.getDimension(R.dimen.tap_boost)
    private var verocity : Float = 20F //初期速度


    //GameManagerクラスから呼び出されUIを変更していく
    override fun draw(canvas: Canvas) {
        //verocityの正負によって表示するgopherを変化させる
        if(verocity >  0F){
            canvas.drawBitmap(gopher_red, gopherX, gopherY, null)
        }else{
            canvas.drawBitmap(gopher_yellow, gopherX, gopherY, null)
        }
    }
    override fun update() {
        gopherY += verocity
        verocity += gravity * 0.1F
    }

    fun onTouchEvent(){
        gopherY += tapBoost
        verocity -= 20F
    }

}