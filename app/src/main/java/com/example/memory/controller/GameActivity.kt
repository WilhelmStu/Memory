package com.example.memory.controller

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memory.R
import com.example.memory.modell.MainModel
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.random.Random


private const val tag = "GameActivity"
private var cardClicked = false
private var arr = IntArray(0)
private var randArr = IntArray(0)
private var firstView: ImageView? = null
private var remainingPairs = 0

/** ----Animation---- */
private var mSetRightOut: AnimatorSet? = null
private var mSetLeftIn: AnimatorSet? = null
private var mIsBackVisible = false

class GameActivity : AppCompatActivity(), CustomOnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // get selected size from intent (SMALL if not specified)
        init(intent.getIntExtra("SIZE", MainModel.SMALL))
        mSetRightOut = AnimatorInflater.loadAnimator(this, R.animator.out_animation) as AnimatorSet?
        mSetLeftIn = AnimatorInflater.loadAnimator(this, R.animator.in_animation) as AnimatorSet?

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
        if (!cardClicked) { // first card selected
            cardClicked = true
            changeCameraDistance(view)
            flipCard(view)
            firstView = view


        } else { // second card selected
            changeCameraDistance(view)
            flipCard(view)
        }


        //view.setImageResource(R.drawable.twitter)

    }

    private fun getRandomMemoryOrder(size: Int) {

        randArr = IntArray(size)
        val rng = Random
        for (int in arr.indices) { // fill array with values
            arr[int] = (int % (size / 2)) // each value 2 times
        }
        for (index in randArr.indices) { // shuffle all the values
            val randomIndex = rng.nextInt(randArr.size)
            val temp = randArr[index]
            randArr[index] = randArr[randomIndex]
            randArr[randomIndex] = temp
        }
    }

    private fun changeCameraDistance(view: ImageView) {
        val distance = 8000
        val scale: Float = resources.displayMetrics.density * distance
        view.cameraDistance = scale
    }

    private fun flipCard(view: ImageView) {
        mIsBackVisible = if (!mIsBackVisible) {
            mSetRightOut?.setTarget(view)
            mSetLeftIn?.setTarget(view)
            mSetRightOut?.start()
            mSetRightOut?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.setImageResource(R.drawable.reddit)

                    mSetLeftIn?.start()
                }
            })

            true
        } else {
            mSetRightOut?.setTarget(view)
            mSetLeftIn?.setTarget(view)
            mSetRightOut?.start()
            mSetRightOut?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.setImageResource(R.drawable.memory_card_back_v1)

                    mSetLeftIn?.start()
                }
            })
            false
        }
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