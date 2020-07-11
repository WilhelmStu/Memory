package com.example.memory

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat.startActivity


class About(var c: Activity) : Dialog(c) {

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.toast_layout)
        val close = findViewById<Button>(R.id.close)
        val link1 = findViewById<ImageButton>(R.id.imageButton)
        val link2 = findViewById<ImageButton>(R.id.imageButton2)
        val link3 = findViewById<ImageButton>(R.id.imageButton3)
        close.setOnClickListener {
            dismiss()
        }

        link1.setOnClickListener {
            goToUrl("https://www.reddit.com/")
        }
        link2.setOnClickListener {
            goToUrl("https://www.twitter.com/")
        }

        link3.setOnClickListener {
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