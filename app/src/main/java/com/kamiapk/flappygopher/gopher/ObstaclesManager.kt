package com.kamiapk.flappygopher.gopher

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Rect
import com.kamiapk.flappygopher.GameManagerCallback
import com.kamiapk.flappygopher.R
import kotlin.random.Random

/*
ここでobstacleのインスタンスを取得し

 */
class ObstaclesManager(resources: Resources,screenHeight : Int, screenWidth : Int, callback : GameManagerCallback) : CallbackObstacle{

    private var obstacles = ArrayList<Obstacle>()
    //新しくObstacleを作成するのに用いる
    private val resources = resources
    private val screenHeight = screenHeight
    private val screenWidth = screenWidth

    private var interval = resources.getDimension(R.dimen.obstacle_interval).toInt()  - Random.nextInt(500)

    private var  progress = 0
    private val speed: Int = resources.getDimension(R.dimen.obstacle_speed).toInt()

    private val gameManagerCallback = callback


    fun draw(canvas: Canvas) {
        for (obstacle in obstacles) {
            obstacle.draw(canvas)
        }
    }

    fun update(){
        progress += speed
        if(progress > interval){
            //インターバルの変更
            interval = resources.getDimension(R.dimen.obstacle_interval).toInt()  - Random.nextInt(500)
            progress = 0
            //obstacleの生成
            obstacles.add(Obstacle(resources,screenHeight,screenWidth,this))
        }

        //obstacleは消されることもあるので衝突を避けるために新しいリストを作って衝突を回避
        //ここは値渡ししなければならない.衝突回避のため
        val avoidList: ArrayList<Obstacle> = arrayListOf()
        avoidList.addAll(obstacles)

        for (obstacle in avoidList) {
            obstacle.update()
        }

    }

    //obstacleが画面外に出たときの処理
    override fun removeObstacle(obstacle :Obstacle){
        //リストからObstacleオブジェクトを消す
        obstacles.remove(obstacle)
        gameManagerCallback.removeObstacle(obstacle)
    }

    override fun updatePosition(obstacle: Obstacle, positions: ArrayList<Rect>) {
            gameManagerCallback.upDatePosition(obstacle,positions)
        }

    

}