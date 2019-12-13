package com.kamiapk.flappygopher.gopher

import android.graphics.Canvas

//画面に表示するオブジェクトのインターフェイスとして用意
interface DisplayObject {

    fun draw(canvas : Canvas)

    fun update()

}