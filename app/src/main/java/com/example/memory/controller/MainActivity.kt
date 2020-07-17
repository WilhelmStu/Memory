package com.example.memory.controller


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.example.memory.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val animation = AnimationHandler(this, IntArray(0))

        aboutButton.setOnClickListener {
            AboutDialog(this).show()
        }

        newGameButton.setOnClickListener {
            NewGameDialog(this).show()
        }

        var state = false
        colorButton.setOnClickListener {
            state = if (!state) {
                imageView.setImageResource(R.drawable.memory_with_shadow_blue)
                newGameButton.setImageResource(R.drawable.play_now_blue)
                colorButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
                !state
            } else {
                imageView.setImageResource(R.drawable.memory_with_shadow)
                newGameButton.setImageResource(R.drawable.play_now)
                colorButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlue))
                !state
            }
        }

        animation.playNowButton(newGameButton)


    }
}