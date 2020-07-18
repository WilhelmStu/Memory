package com.example.memory.controller.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.os.Bundle
import android.util.Log
import android.view.Window
import com.example.memory.R
import com.example.memory.controller.GameActivity
import com.example.memory.controller.MainActivity
import com.example.memory.modell.MainModel
import kotlinx.android.synthetic.main.victory_dialog.*


class VictoryDialog(c: Activity, private val size: Int, private val turnCount: Int) : Dialog(c) {

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.victory_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val text = "Congratulations, you won the game in $turnCount turns!"
        congratulations.text = text

        menu.setOnClickListener {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
        restart.setOnClickListener {
            context.startActivity(Intent(context, GameActivity::class.java).putExtra("SIZE", size))
        }

    }
}