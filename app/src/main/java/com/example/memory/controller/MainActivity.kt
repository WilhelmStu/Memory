package com.example.memory.controller


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.memory.R
import com.example.memory.controller.dialogs.AboutDialog
import com.example.memory.controller.dialogs.NewGameDialog
import com.example.memory.modell.MainModel
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

        colorButton.setOnClickListener {
            MainModel.defaultColor = !MainModel.defaultColor
            setColor()
        }

        animation.playNowButton(newGameButton)
    }

    override fun onRestart() {
        super.onRestart()
        setColor()
    }

    override fun onResume() {
        super.onResume()
        setColor()
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

    private fun setColor() {
        if (!MainModel.defaultColor) {
            imageView.setImageResource(MainModel.MEMORY_BLUE)
            newGameButton.setImageResource(MainModel.PLAY_BUTTON_BLUE)
            colorButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))

        } else {
            imageView.setImageResource(MainModel.MEMORY_RED)
            newGameButton.setImageResource(MainModel.PLAY_BUTTON_RED)
            colorButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlue))

        }
    }
}