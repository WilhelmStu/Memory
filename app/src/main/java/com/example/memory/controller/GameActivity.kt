package com.example.memory.controller

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memory.R
import com.example.memory.controller.dialogs.BackDialog
import com.example.memory.controller.dialogs.VictoryDialog
import com.example.memory.modell.MainModel
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.random.Random

private const val tag = "GameActivity"
private var firstCardID = -1
private var arr = IntArray(0)
private var randArr = IntArray(0)
private var firstView: ImageView? = null
private var prevView1: ImageView? = null
private var prevView2: ImageView? = null
private var prevView1ID = -1
private var prevView2ID = -1
private var remainingPairs = 0
private var animation: AnimationHandler? = null
private var turnCount = 0

/**
 * GameActivity, main activity where the game is played
 */
class GameActivity : AppCompatActivity(), CustomOnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // init, get selected size from intent (SMALL if not specified)
        init(intent.getIntExtra("SIZE", MainModel.SMALL))
        turnCount = 0
        // initialise animationHandler
        animation = AnimationHandler(this, randArr)

    }


    private fun init(size: Int) {
        Log.i(tag, "Size is: $size")
        // get the amount of columns based on size
        var columns = MainModel.COLUMN_SMALL
        if (size == MainModel.MEDIUM) columns = MainModel.COLUMN_MEDIUM
        if (size == MainModel.LARGE) columns = MainModel.COLUMN_LARGE
        // set the layoutManager to be gridLayout
        recyclerView.layoutManager = GridLayoutManager(baseContext, columns)

        getRandomMemoryOrder(size)
        remainingPairs = size / 2
        arr = IntArray(size)
        for (int in arr.indices) {
            arr[int] = int
        }
        // set adapter of recyclerView
        val adapter = CustomAdapter(arr, this)
        recyclerView.adapter = adapter
    }

    /**
     * Called from CustomAdapter, whenever a card os clicked
     */
    override fun onCardClicked(i: Int, view: ImageView) {
        Log.i(tag, "Card clicked, ID: $i")
        if (arr[i] == -1 || firstCardID == i) {
            Log.i(tag, "already found // clicked first card again")
            return
        }

        // make sure animations don't leave cards in unwanted states
        checkForWrongCardAlignment(view)

        if (firstCardID == -1) { // first card selected
            Log.i(tag, "is first card")
            firstCardID = i

            Handler().postDelayed({ // make sure previous second cards state is correct
                prevView2?.setImageResource(MainModel.card_back_texture)
            }, 100)

            changeCameraDistance(view)
            animation?.flipCard(view, i)
            firstView = view
        } else { // second card selected, do animation and check for pair
            Log.i(tag, "is second card")
            changeCameraDistance(view)

            // make sure first card is aligned correctly (only maters if player is clicking to fast)
            firstView?.setImageResource(MainModel.cardTextures[randArr[firstCardID]])
            firstView?.rotation = 0F
            firstView?.rotationY = 0F

            if (randArr[firstCardID] == randArr[i]) { // got a pair -> make them vanish
                Log.i(tag, "got a pair!! ( ${randArr[firstCardID]}, ${randArr[i]})")
                arr[firstCardID] = -1
                arr[i] = -1
                animation?.flipAndFadeOut(firstView, view, i)
                remainingPairs--
                Toast.makeText(
                    this, "Got a pair!",
                    Toast.LENGTH_SHORT
                ).show();
            } else { // not a pair flip them back..
                Log.i(tag, "not a pair!")
                animation?.shakeAndFlipCardsBack(firstView, view, i)
            }
            val text = "Turn Count: ${++turnCount}"
            turnCountView.text = text
            prevView1 = firstView
            prevView2 = view
            prevView1ID = firstCardID
            prevView2ID = i
            firstCardID = -1
            firstView = null

        }
        if (remainingPairs == 0) {
            val tmp = turnCount
            turnCount = 0
            Handler().postDelayed({ // make sure previous second cards state is correct
                VictoryDialog(
                    this,
                    intent.getIntExtra("SIZE", MainModel.SMALL),
                    tmp
                ).show()
            }, 300)

        }

    }

    private fun getRandomMemoryOrder(size: Int) {
        randArr = IntArray(size)
        val rng = Random

        for (int in randArr.indices) { // fill array with values
            randArr[int] = (int % (size / 2)) // each value 2 times
        }
        for (index in randArr.indices) { // shuffle all the values
            val randomIndex = rng.nextInt(randArr.size)
            val temp = randArr[index]
            randArr[index] = randArr[randomIndex]
            randArr[randomIndex] = temp
        }
        Log.i(tag, "randArray: ${randArr.contentToString()}")
    }

    /**
     * Change camera distance for animation, since to small distance can cause cut off animation
     */
    private fun changeCameraDistance(view: ImageView) {
        val distance = 8000
        val scale: Float = resources.displayMetrics.density * distance
        view.cameraDistance = scale
    }

    private fun checkForWrongCardAlignment(view: ImageView) {
        if (prevView1ID != -1 && arr[prevView1ID] == -1) {
            prevView1?.alpha = 0F
            prevView2?.alpha = 0F
            return
        }

        if (prevView1 != null && !(prevView1?.equals(view)!!)) {
            resetAsset(prevView1)
        }
        if (prevView2 != null && !(prevView2?.equals(view)!!)) {
            resetAsset(prevView2)
        }
    }

    private fun resetAsset(view: ImageView?) {
        view?.setImageResource(MainModel.card_back_texture)
        view?.rotation = 0F
        view?.rotationY = 0F
    }

    override fun onBackPressed() {
        BackDialog(this).show()
    }
    override fun onRestart() {
        super.onRestart()
        turnCount = 0
    }
}
