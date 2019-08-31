package com.kamiapk.flappygopher.gopher

import android.graphics.Canvas

//gopher.ktのインターフェイスとして用意
interface DisplayObject {

    fun draw(canvas : Canvas)

    fun update()

}