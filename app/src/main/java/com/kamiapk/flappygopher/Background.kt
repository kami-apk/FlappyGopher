package com.kamiapk.flappygopher

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.kamiapk.flappygopher.gopher.DisplayObject




class Background(resources : Resources, screenHeight : Int) : DisplayObject {

    //画面の高さ(y軸の長さ)
    private val screenHeight = screenHeight.toFloat()

    //画像リソースからビットマップとして呼び出す
    private val background_top : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.mmm)
    private val background_bottom : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.mmm)
    //端末に依存した長さを取り出す
    private val topHeight : Int = resources.getDimension(R.dimen.background_top_height).toInt()
    private val bottomHeight : Int = resources.getDimension(R.dimen.background_bottom_height).toInt()
    //端末に合わせてBitmapの大きさを変える
    private val top = Bitmap.createScaledBitmap(background_top, background_top.width, topHeight,false)
    private val bottom = Bitmap.createScaledBitmap(background_bottom, background_bottom.width, bottomHeight, false)

    //backgroundにBitmapを表示させる
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(top,0F,0F,null)
        canvas.drawBitmap(bottom,0F,screenHeight - bottom.height,null)
        //タブレット用の表示
        canvas.drawBitmap(top,top.width.toFloat(),0F,null)
        canvas.drawBitmap(bottom,bottom.width.toFloat(),screenHeight - bottom.height,null)
    }

    override fun update() {

    }

}