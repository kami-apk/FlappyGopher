package com.kamiapk.flappygopher

import android.graphics.Rect
import com.kamiapk.flappygopher.gopher.Obstacle

interface GameManagerCallback {

    fun upDatePosition(gopherPosition : Rect)
    fun upDatePosition(obstacle : Obstacle, positions : ArrayList<Rect>)
    fun removeObstacle(obstacle : Obstacle)

}

