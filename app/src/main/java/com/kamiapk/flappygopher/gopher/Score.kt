package com.kamiapk.flappygopher.gopher

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas




const val PrefKey = "BEST"

class Score(resources: Resources, private val screenHeight: Int, private val screenWidth: Int) : DisplayObject {
    override fun update() {
    }


    //Bitmapと実数字の紐付け
    private var map = HashMap<Int, Bitmap>()
    //bitmap
    private var zero : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.zero)
    private var one : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.one)
    private var two : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.two)
    private var three : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.three)
    private var four : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.four)
    private var five : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.five)
    private var six : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.six)
    private var seven : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.seven)
    private var eight : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.eight)
    private var nine : Bitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.nine)

    init {
        map[0] = zero
        map[1] = one
        map[2] = two
        map[3] = three
        map[4] = four
        map[5] = five
        map[6] = six
        map[7]= seven
        map[8] = eight
        map[9] = nine
    }


    private val scoreBitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.score)
    private val bestBitmap = BitmapFactory.decodeResource(resources, com.kamiapk.flappygopher.R.drawable.best)

    private var gameScore = 0
    private var best = 0
    private var collision = false

    private var stringScore = ""









    override fun draw(canvas: Canvas) {
        if (!collision) {
            val displayBitmaps = displayScore(gameScore)
            var i = 0
            while (i < displayBitmaps.size) {
                val xPosition =
                    (screenWidth / 2 - displayBitmaps.size * zero.width / 2 + zero.width * i).toFloat()
                canvas.drawBitmap(displayBitmaps[i], xPosition, (screenHeight / 4).toFloat(), null)
                i++
            }
        } else {//最終スコア表示
            //スコアの取得
            val currentDigits = displayScore(gameScore)
            val topDigits = displayScore(best)

            //各種得点
            canvas.drawBitmap(
                scoreBitmap,
                (screenWidth / 4 - scoreBitmap.width / 2).toFloat(),
                (3 * screenHeight / 4 - zero.height - scoreBitmap.height).toFloat(),
                null
            )
            canvas.drawBitmap(
                bestBitmap,
                (3 * screenWidth / 4 - bestBitmap.width / 2).toFloat(),
                (3 * screenHeight / 4 - zero.height - bestBitmap.height).toFloat(),
                null
            )

            //得点
            for (i in 0 until topDigits.size) {
                val x = (3 * screenWidth / 4 - topDigits.size * zero.width + zero.width * i).toFloat()
                canvas.drawBitmap(topDigits[i], x, (3 * screenHeight / 4).toFloat(), null)
            }

            for (i in 0 until currentDigits.size) {
                val x = (screenWidth / 4 - currentDigits.size * zero.width + zero.width * i).toFloat()
                canvas.drawBitmap(currentDigits[i], x, (3 * screenHeight / 4).toFloat(), null)
            }





        }
    }







    //scoreを受け取って表示させるBitmapを作成する
    private fun displayScore(gameScore: Int) : ArrayList<Bitmap> {
        var bitmapScore = ArrayList<Bitmap>()

        stringScore = gameScore.toString()

        var i = 0
        while(i < stringScore.length){
            //charをstringに変換しないとintに変換できないみたい
            bitmapScore.add(map[stringScore[i].toString().toInt()] ?: zero)
            i++
        }

        return bitmapScore
    }

    fun updateScore(gameScore: Int) {
        this.gameScore = gameScore
    }

    @SuppressLint("CommitPrefEdits")
    fun collision(pref : SharedPreferences){
        collision = true
        best = pref.getInt(PrefKey,0)
        //bestScoreの更新があったら
        if( best <= gameScore){
            pref.edit().putInt(PrefKey,gameScore).apply()
            best = gameScore
        }
    }


}