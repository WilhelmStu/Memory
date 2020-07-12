package com.example.memory.controller

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri

import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat.startActivity
import com.example.memory.R
import kotlinx.android.synthetic.main.toast_layout.*


class About(var c: Activity) : Dialog(c) {

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.toast_layout)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        cancel.setOnClickListener {
            dismiss()
        }
        imageButton.setOnClickListener {
            goToUrl("https://www.reddit.com/")
        }
        imageButton2.setOnClickListener {
            goToUrl("https://www.twitter.com/")
        }
        imageButton3.setOnClickListener {
            sendMail()
        }
    }

    private fun goToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(c, intent, onSaveInstanceState())
    }

    private fun sendMail() {
        val intent =
            Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "wistuhlpfarr@edu.aau.at", null))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(Intent.EXTRA_TEXT, "Hello there!")
        startActivity(c, intent, onSaveInstanceState())
    }


}