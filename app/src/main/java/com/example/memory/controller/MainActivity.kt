package com.example.memory.controller


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memory.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        aboutButton.setOnClickListener {
            About(this).show()
        }

        newGameButton.setOnClickListener {
            NewGameDialog(this).show()
        }


    }
}