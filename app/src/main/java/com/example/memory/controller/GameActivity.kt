package com.example.memory.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginStart
import androidx.core.view.setMargins
import com.example.memory.R
import com.example.memory.modell.MainModel
import kotlinx.android.synthetic.main.activity_game.*

private val tag = "GameActivity"

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        constr.addView(layoutInflater.inflate(R.layout.game_size_small, null))

        init(intent.getIntExtra("SIZE", MainModel.SMALL))
    }


    private fun init(size: Int) {
        Log.i(tag, "Size is: $size")


    }
}