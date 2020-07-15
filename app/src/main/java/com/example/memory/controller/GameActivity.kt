package com.example.memory.controller

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memory.R
import com.example.memory.modell.MainModel
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.random.Random


private const val tag = "GameActivity"
private var firstCardID = -1
private var secondCardID = -1
private var arr = IntArray(0)
private var randArr = IntArray(0)
private var firstView: ImageView? = null
private var remainingPairs = 0
private val model = MainModel()

/** ----Animation---- */
private var animFlipOut: AnimatorSet? = null
private var animFlipIn: AnimatorSet? = null
private var animFlipOutSecond: AnimatorSet? = null
private var animFlipInSecond: AnimatorSet? = null
private var animShake: AnimatorSet? = null
private var animShakeSecond: AnimatorSet? = null
private var animFadeOut: AnimatorSet? = null
private var animFadeOutSecond: AnimatorSet? = null


class GameActivity : AppCompatActivity(), CustomOnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // get selected size from intent (SMALL if not specified)
        init(intent.getIntExtra("SIZE", MainModel.SMALL))
        animFlipOut = AnimatorInflater.loadAnimator(this, R.animator.out_animation) as AnimatorSet?
        animFlipIn = AnimatorInflater.loadAnimator(this, R.animator.in_animation) as AnimatorSet?
        animFlipOutSecond = AnimatorInflater.loadAnimator(this, R.animator.out_animation) as AnimatorSet?
        animFlipInSecond = AnimatorInflater.loadAnimator(this, R.animator.in_animation) as AnimatorSet?
        animShake = AnimatorInflater.loadAnimator(this, R.animator.shake_animation) as AnimatorSet?
        animShakeSecond = AnimatorInflater.loadAnimator(this, R.animator.shake_animation) as AnimatorSet?
        animFadeOut = AnimatorInflater.loadAnimator(this, R.animator.fadeout_animation) as AnimatorSet?
        animFadeOutSecond = AnimatorInflater.loadAnimator(this, R.animator.fadeout_animation) as AnimatorSet?

    }


    private fun init(size: Int) {
        Log.i(tag, "Size is: $size")
        var columns = 4
        if (size == MainModel.MEDIUM) columns = 5
        if (size == MainModel.LARGE) columns = 6
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
        if (arr[i] == -1) {
            Log.i(tag, "already found")
            return
        }
        if (firstCardID == -1) { // first card selected
            Log.i(tag, "is first card")
            firstCardID = i
            changeCameraDistance(view)
            flipCard(view, i)
            firstView = view
        } else if (firstCardID == i) { // first card selected again do nothing
            Log.i(tag, "first card clicked again")
        } else { // second card selected, do animation and check for pair
            Log.i(tag, "is second card")
            changeCameraDistance(view)
            secondCardID = i
            if (randArr[firstCardID] == randArr[i]) { // got a pair -> make them vanish
                Log.i(tag, "got a pair!! ( ${randArr[firstCardID]}, ${randArr[i]})")
                arr[firstCardID] = -1
                arr[i] = -1
                flipAndFadeOut(firstView, view, i)
            } else { // not a pair flip them back..
                Log.i(tag, "not a pair!")
                shakeAndflipCardsBack(firstView, view, i)


            }
            firstCardID = -1
            secondCardID = -1
            firstView = null

        }
        if (checkForVictory()) {
            Toast.makeText(this, "VICTORY!!", Toast.LENGTH_LONG).show()
        }

    }

    private fun checkForVictory(): Boolean {
        for (index in arr.indices) {
            if (arr[index] != -1) {
                return false
            }
        }
        return true
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

    /**
     * Flips the first selected card
     */
    private fun flipCard(view: ImageView, id: Int) {

        animFlipOut?.setTarget(view)
        animFlipIn?.setTarget(view)
        animFlipOut?.startDelay = 0
        animFlipOut?.start()

        animFlipOut?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.setImageResource(model.cardTextures[randArr[id]])
                animFlipIn?.start()
            }
        })
    }

    /**
     * Flips second card and lets them fade out since they are a pair
     */
    private fun flipAndFadeOut(firstView: ImageView?, secondView: ImageView?, idSecond: Int) {

        animFlipOut?.setTarget(secondView)
        animFlipIn?.setTarget(secondView)
        animFlipOut?.startDelay = 0
        animFlipOut?.start()


        animFlipOut?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                secondView?.setImageResource(model.cardTextures[randArr[idSecond]])
                animFlipIn?.start()
            }
        })

        animFadeOut?.setTarget(firstView)
        animFadeOutSecond?.setTarget(secondView)
        animFadeOut?.playTogether(animFadeOutSecond)
        animFadeOut?.startDelay = 1000
        animFadeOut?.start()

    }

    /**
     * Flips the second selected card and then flips both the first and second one back
     */
    private fun shakeAndflipCardsBack(
        firstView: ImageView?,
        secondView: ImageView?,
        idSecond: Int
    ) {

        animFlipOut?.setTarget(firstView)
        animFlipIn?.setTarget(firstView)
        animFlipOutSecond?.setTarget(secondView)
        animFlipInSecond?.setTarget(secondView)
        animFlipOutSecond?.start()
        animFlipOutSecond?.removeAllListeners()
        animFlipOutSecond?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                secondView?.setImageResource(model.cardTextures[randArr[idSecond]])
                animFlipInSecond?.start()
            }
        })

        // shakes the cards
        animShake?.setTarget(firstView)
        animShakeSecond?.setTarget(secondView)
        animShake?.playTogether(animShakeSecond)
        animShake?.startDelay = 1000
        animShake?.start()

        animFlipOut?.playTogether(animFlipOutSecond)
        animFlipOut?.startDelay = 2000
        animFlipOut?.start()
        animFlipOut?.removeAllListeners()
        animFlipOut?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                firstView?.setImageResource(R.drawable.memory_card_back_v1)
                secondView?.setImageResource(R.drawable.memory_card_back_v1)
                animFlipIn?.playTogether(animFlipInSecond)
                animFlipIn?.start()
            }
        })
    }
}


// todo Counter with required turns
// todo database with highscores, and name who did it (in main menu)
// todo make "sure you want to end this game?" dialog when pressing back..


// todo save more stuff in model!!

// todo make toast to show if pair/notpair
