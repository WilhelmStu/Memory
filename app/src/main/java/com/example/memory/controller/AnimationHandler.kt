package com.example.memory.controller

import android.animation.*
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import com.example.memory.R
import com.example.memory.modell.MainModel

/**
 * Class that handles all the animations, using animators defined in xml files
 */
class AnimationHandler(context: Context, private var randArr: IntArray) {

    private var animFlipOut = AnimatorInflater.loadAnimator(context, R.animator.out_animation) as AnimatorSet
    private var animFlipIn = AnimatorInflater.loadAnimator(context, R.animator.in_animation) as AnimatorSet
    private var animFlipOutSecond = AnimatorInflater.loadAnimator(context, R.animator.out_animation) as AnimatorSet
    private var animFlipInSecond = AnimatorInflater.loadAnimator(context, R.animator.in_animation) as AnimatorSet
    private var animShake = AnimatorInflater.loadAnimator(context, R.animator.shake_animation) as AnimatorSet
    private var animShakeSecond = AnimatorInflater.loadAnimator(context, R.animator.shake_animation) as AnimatorSet
    private var animFadeOut = AnimatorInflater.loadAnimator(context, R.animator.fadeout_animation) as AnimatorSet
    private var animFadeOutSecond = AnimatorInflater.loadAnimator(context, R.animator.fadeout_animation) as AnimatorSet

    /**
     * Constantly resize the play now button, with object animator
     */
    fun playNowButton(button: View) {
        val animOut = ObjectAnimator.ofPropertyValuesHolder(
            button,
            PropertyValuesHolder.ofFloat("scaleX", 0.85f),
            PropertyValuesHolder.ofFloat("scaleY", 0.85f)
        )
        animOut.duration = 600
        animOut.repeatMode = ObjectAnimator.REVERSE
        animOut.repeatCount = Animation.INFINITE
        val animSet = AnimatorSet()
        animSet.play(animOut)
        animSet.start()

    }

    /**
     * Flips the first selected card
     */
    fun flipCard(view: ImageView, id: Int) {
        animFlipOut.setTarget(view)
        animFlipIn.setTarget(view)
        animFlipOut.startDelay = 0
        animFlipOut.start()

        animFlipOut.removeAllListeners()
        animFlipOutSecond.removeAllListeners()
        animFlipOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.setImageResource(MainModel.cardTextures[randArr[id]])
                animFlipIn.start()
            }
        })
    }

    /**
     * Flips second card and lets them fade out since they are a pair
     */
    fun flipAndFadeOut(firstView: ImageView?, secondView: ImageView?, idSecond: Int) {
        animFlipOut.setTarget(secondView)
        animFlipIn.setTarget(secondView)
        animFlipOut.startDelay = 0
        animFlipOut.start()

        animFlipOut.removeAllListeners()
        animFlipOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                secondView?.setImageResource(MainModel.cardTextures[randArr[idSecond]])
                animFlipIn.start()
            }
        })

        animFadeOut.setTarget(firstView)
        animFadeOutSecond.setTarget(secondView)
        animFadeOut.playTogether(animFadeOutSecond)
        animFadeOut.startDelay = 800
        animFadeOut.start()
    }

    /**
     * Flips the second selected card and then flips both the first and second one back
     */
    fun shakeAndFlipCardsBack(firstView: ImageView?, secondView: ImageView?, idSecond: Int) {
        animFlipOut.setTarget(firstView)
        animFlipIn.setTarget(firstView)
        animFlipOutSecond.setTarget(secondView)
        animFlipInSecond.setTarget(secondView)

        // flip the second card
        animFlipOutSecond.start()
        animFlipOutSecond.removeAllListeners()
        animFlipOutSecond.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                secondView?.setImageResource(MainModel.cardTextures[randArr[idSecond]])
                animFlipInSecond.start()
            }
        })

        // shakes the cards
        animShake.setTarget(firstView)
        animShakeSecond.setTarget(secondView)
        animShake.playTogether(animShakeSecond)
        animShake.startDelay = 600
        animShake.start()

        // flip both cards back
        animFlipOut.playTogether(animFlipOutSecond)
        animFlipOut.startDelay = 1200
        animFlipOut.start()
        animFlipOut.removeAllListeners()
        animFlipOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                firstView?.setImageResource(R.drawable.memory_card_back_v1)
                secondView?.setImageResource(R.drawable.memory_card_back_v1)
                animFlipIn.playTogether(animFlipInSecond)
                animFlipIn.start()
            }
        })
    }
}


