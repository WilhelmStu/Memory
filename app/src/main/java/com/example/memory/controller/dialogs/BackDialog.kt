package com.example.memory.controller.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.os.Bundle
import android.view.Window
import com.example.memory.R
import com.example.memory.controller.MainActivity
import kotlinx.android.synthetic.main.back_dialog.*

/**
 * Back dialog showing upp when trying to go back to mainMenu
 */
class BackDialog(c: Activity) : Dialog(c) {

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.back_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        backCancel.setOnClickListener {
           dismiss()
        }
        ok.setOnClickListener {
            context.startActivity(Intent(context, MainActivity::class.java))
        }

    }
}