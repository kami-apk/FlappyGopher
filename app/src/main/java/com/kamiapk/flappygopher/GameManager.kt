package com.kamiapk.flappygopher

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Rect
import android.media.SoundPool
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.core.view.isVisible
import com.kamiapk.flappygopher.gopher.*
import kotlinx.android.synthetic.main.activity_main.view.*


/*
    スクリーン上の要素を制御するクラス
 */

class GameManager(context : Context, attributeSet : AttributeSet) : SurfaceView(context) , SurfaceHolder.Callback, GameManagerCallback{


    /*
        遅延初期化
    */
    //Gopherのインスタンス取得!
    private  lateinit var gopher : Gopher
    //Backgroundのインスタンス取得
    private lateinit var  background : Background
    //障害物のインスタンス取得
    private lateinit var obstaclesManager : ObstaclesManager
    //GameOver
    private lateinit var gameOver : GameOver
    //描写ではなく当たり判定用
    private lateinit var gopherPosition: Rect
    private lateinit var obstaclePositions :HashMap<Obstacle, List<Rect>>
    //title
    private lateinit var title : GameMessage
    //scoreインスタンス
    private lateinit var score : Score
    private var gameScore = 0

    private lateinit var sound : SoundManager



    //GameState
    private var gameState = GameState.INITIAL

    //MainThreadのインスタンスを取得
    private var thread = MainThread( holder, this)

    //画面の幅を取得
    private  var screenHeight : Int
    private  var screenWidth : Int

    var stopScreenTap = 0L

    init{
        screenHeight = getScreenHeight()
        screenWidth = getScreenWidth()
        //getHolderとaddCallbackを結びつける
        holder.addCallback(this)
        //ゲーム開始
        this.background = Background(resources,screenHeight, screenWidth)
        initGame()
    }

    //ゲームオーバー　→　ゲームスタート　において初期化が必要のため
    fun initGame() {
        //タイトル画面
        title = GameMessage(resources,screenHeight, screenWidth)
        //Gopherのインスタンス取得!
        gopher = Gopher(resources,screenHeight,this)

        //障害物のインスタンス取得
        obstaclesManager = ObstaclesManager(resources, screenHeight, screenWidth ,this)
        //GameOver
        gameOver = GameOver(resources,screenHeight, screenWidth)
        //描写ではなく当たり判定用
        gopherPosition= Rect()
        obstaclePositions = HashMap<Obstacle, List<Rect>>()
        score = Score(resources,screenHeight, screenWidth)
        gameScore = 0
        SoundManager.getInstance(context)
        stopScreenTap = 0
    }


    override fun surfaceCreated(p0: SurfaceHolder?) {
        //アプリをいったんバックグラウンドにしてしまったときに戻ると落ちるのでその対策
        if(thread.state == Thread.State.TERMINATED){
            thread = MainThread( holder, this)
        }
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        var retry = true
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
        stopScreenTap++
        background.update()
        //GameState の　状態により行動を変化させる
        when (gameState){
            GameState.PLAYING -> {
                //Gopherクラスのupdate()を呼び出す
                gopher.update()
                //障害物を動かす
                obstaclesManager.update()
                //状態を変化させたら下のdrawメソッドが呼ばれて画面委描写される
            }
            GameState.GAME_OVER -> {
                gopher.update()
            }
            else -> {

            }
        }

    }

    //画面描写処理
    override fun draw(canvas: Canvas){
        super.draw(canvas)

        background.draw(canvas)

        when ( gameState ) {

            GameState.PLAYING -> {
                //画面にgopher君を呼び出すだけ
                //動作を付けるのはgopher.update()
                gopher.draw(canvas)
                obstaclesManager.draw(canvas)
                score.draw(canvas)
                checkCollision()
            }

            GameState.GAME_OVER -> {
                gopher.draw(canvas)
                obstaclesManager.draw(canvas)
                score.draw(canvas)
                gameOver.draw(canvas)
            }
            GameState.INITIAL -> {
                gopher.draw(canvas)
                title.draw(canvas)
            }

        }
    }

    //画面がタッチされた時のイベント
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (gameState) {
            GameState.PLAYING -> {
                //上昇させる
                gopher.onTouchEvent()
                SoundManager.getInstance(context).playSound(SoundManager.SOUND_WING)
            }
            GameState.INITIAL -> {
                //連続タップで画面遷移がすぐに起きるのを防ぐ
                if(stopScreenTap >= 20) {  //0.33秒以上
                    gameState = GameState.PLAYING
                }
            }
            GameState.GAME_OVER -> {
                if(stopScreenTap >= 30){ //0.5秒以上
                    initGame()
                    gopher.gopherY = (screenHeight / 2 ).toFloat()
                    gopher.collision = false
                    gameState = GameState.INITIAL
                }
            }
        }
        return super.onTouchEvent(event)
    }

    //画面の高さ・幅を取得
    private fun getScreenHeight() : Int {
        val dm = Resources.getSystem().displayMetrics
        return dm.heightPixels
    }
    private fun getScreenWidth() : Int {
        val dm = Resources.getSystem().displayMetrics
        return dm.widthPixels
    }





    override fun upDatePosition(gopherPosition: Rect) {
        this.gopherPosition = gopherPosition
    }

    override fun upDatePosition(obstacle: Obstacle, positions: ArrayList<Rect>) {
        if(obstaclePositions.containsKey(obstacle)) {
            obstaclePositions.remove(obstacle)
        }
        obstaclePositions[obstacle] = positions
    }

    override fun removeObstacle(obstacle: Obstacle) {
        SoundManager.getInstance(context).playSound(SoundManager.SFX_POINT)
        obstaclePositions.remove(obstacle)
        gameScore++
        score.updateScore(gameScore)
    }

    //衝突に対する制御
    private fun checkCollision(){
        var collision = false
        if(gopherPosition.bottom > screenHeight ){
            collision = true
        }else{
            //各々のobstacleと検証
            for(obstacle in obstaclePositions.keys ) {
                //上下のパイプについてそれぞれ考える
                val bottomRectangle = obstaclePositions[obstacle]?.get(0)
                val topRectangle = obstaclePositions[obstacle]?.get(1)

                bottomRectangle?.apply{
                    topRectangle?.apply{
                        //上下のobstacleに同時に触れることはない
                        if (gopherPosition.right > bottomRectangle.left && gopherPosition.left < bottomRectangle.right && gopherPosition.bottom > bottomRectangle.top) {
                            collision = true
                        } else if (gopherPosition.right > topRectangle.left && gopherPosition.left < topRectangle.right && gopherPosition.top < topRectangle.bottom) {
                            collision = true
                        }
                    }
                }
            }
        }

        if(collision){
            //連続画面遷移を防止するため
            stopScreenTap = 0
            //各種クラスのcollisionフラグをtrueにする
            gameState = GameState.GAME_OVER
            gopher.collision()
            score.collision(context.getSharedPreferences("GET",Context.MODE_PRIVATE))
            SoundManager.getInstance(context).playSound(SoundManager.SFX_DIE)
            SoundManager.getInstance(context).playSound(SoundManager.SFX_HIT)
        }


    }


}