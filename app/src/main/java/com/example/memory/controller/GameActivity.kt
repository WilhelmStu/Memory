package com.example.memory.controller

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memory.R
import com.example.memory.modell.MainModel
import kotlinx.android.synthetic.main.activity_game.*
import java.lang.Thread.sleep
import kotlin.random.Random


private const val tag = "GameActivity"
private var firstCardID = -1
private var arr = IntArray(0)
private var randArr = IntArray(0)
private var firstView: ImageView? = null
private var remainingPairs = 0

/** ----Animation---- */
private var mSetRightOut: AnimatorSet? = null
private var mSetLeftIn: AnimatorSet? = null
private var mSetRightOutView2: AnimatorSet? = null
private var mSetLeftInView2: AnimatorSet? = null

class GameActivity : AppCompatActivity(), CustomOnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // get selected size from intent (SMALL if not specified)
        init(intent.getIntExtra("SIZE", MainModel.SMALL))
        mSetRightOut = AnimatorInflater.loadAnimator(this, R.animator.out_animation) as AnimatorSet?
        mSetLeftIn = AnimatorInflater.loadAnimator(this, R.animator.in_animation) as AnimatorSet?
        mSetRightOutView2 = AnimatorInflater.loadAnimator(this, R.animator.out_animation) as AnimatorSet?
        mSetLeftInView2 = AnimatorInflater.loadAnimator(this, R.animator.in_animation) as AnimatorSet?

    }


    private fun init(size: Int) {
        Log.i(tag, "Size is: $size")
        var columns = 4
        if (size == MainModel.LARGE) columns = 5
        recyclerView.layoutManager = GridLayoutManager(baseContext, columns)

        getRandomMemoryOrder(size)
        remainingPairs = size / 2
        arr = IntArray(size)
        for (int in arr.indices) {
            arr[int] = int
        }
        val adapter = CustomAdapter(arr, this)
        recyclerView.adapter = adapter

    }

    override fun onCardClicked(i: Int, view: ImageView) {
        Log.i(tag, "Card clicked, ID: $i")
        if (firstCardID == -1) { // first card selected
            Log.i(tag, "is first card")
            firstCardID = i
            changeCameraDistance(view)
            flipCard(view)
            firstView = view


        } else if (firstCardID == i) { // first card selected again do nothing
            Log.i(tag, "first card clicked again")
        } else { // second card selected, do animation and check for pair
            Log.i(tag, "is second card")
            changeCameraDistance(view)
            if (randArr[firstCardID] == randArr[i]) { // got a pair -> make them vanish
                Log.i(tag, "got a pair!! ( ${randArr[firstCardID]}, ${randArr[i]})")
                flipCard(view)
            } else { // not a pair flip them back.. //todo shake cards
                Log.i(tag, "not a pair!")
                flipCardsBack(firstView, view)



            }
            firstCardID = -1
            firstView = null

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

    private fun changeCameraDistance(view: ImageView) {
        val distance = 8000
        val scale: Float = resources.displayMetrics.density * distance
        view.cameraDistance = scale
    }

    private fun flipCard(view: ImageView) {

        mSetRightOut?.setTarget(view)
        mSetLeftIn?.setTarget(view)
        mSetRightOut?.startDelay = 0
        mSetRightOut?.start()

        mSetRightOut?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.setImageResource(R.drawable.reddit)
                mSetLeftIn?.start()
            }
        })
    }

    private fun flipCardsBack(firstView: ImageView?, secondView: ImageView?) {

        mSetRightOut?.setTarget(firstView)
        mSetLeftIn?.setTarget(firstView)
        mSetRightOutView2?.setTarget(secondView)
        mSetLeftInView2?.setTarget(secondView)
        mSetRightOutView2?.start()
        mSetRightOutView2?.removeAllListeners()
        mSetRightOutView2?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                secondView?.setImageResource(R.drawable.reddit) // todo make model save selected asset
                mSetLeftInView2?.start()
            }
        }) // todo shake animation
        mSetRightOut?.playTogether(mSetRightOutView2)
        mSetRightOut?.startDelay = 2000
        mSetRightOut?.start()
        mSetRightOut?.removeAllListeners()
        mSetRightOut?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                firstView?.setImageResource(R.drawable.memory_card_back_v1) // todo make model save selected asset
                secondView?.setImageResource(R.drawable.memory_card_back_v1)
                mSetLeftIn?.playTogether(mSetLeftInView2)
                mSetLeftIn?.start()
            }
        })
    }
}


// todo assign numbers to each item in int array above
// todo 2 matching numbers
// todo animation with a card flip
// todo cards vanish when equal
// todo Counter with required turns
// todo database with highscores, and name who did it (in main menu)
// todo make "sure you want to end this game?" dialog when pressing back..

// todo improve layouts maybe make a third one
// todo save more stuff in model!!
// todo make toast to show if pair/notpair
