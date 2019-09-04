package com.kamiapk.flappygopher.gopher

import android.graphics.Rect

interface CallbackObstacle {
    fun removeObstacle(obstacle: Obstacle)
    fun updatePosition(obstacle: Obstacle,positions : ArrayList<Rect>)
}