package com.example.memory.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginStart
import androidx.core.view.setMargins
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memory.R
import com.example.memory.modell.MainModel
import kotlinx.android.synthetic.main.activity_game.*

private val tag = "GameActivity"

class GameActivity : AppCompatActivity(), CustomOnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        init(intent.getIntExtra("SIZE", MainModel.SMALL))
    }


    private fun init(size: Int) {
        Log.i(tag, "Size is: $size")
        var columns = 4
        if (size == MainModel.MEDIUM || size == MainModel.LARGE) columns = 5
        recyclerView.layoutManager = GridLayoutManager(baseContext, columns)
        val arr = IntArray(size)
        for (int in arr.indices){
            arr[int] = int
        }
        val adapter = CustomAdapter(arr, this)
        recyclerView.adapter = adapter


    }

    override fun onCardClicked(i: Int, view: ImageView) {
        view.setImageResource(R.drawable.twitter)
        // todo assign numbers to each item in int array above
        // todo 2 matching numbers
        // todo animation with a card flip
        // todo cards vanish when equal
        // todo Counter with required turns
        // todo database with highscores, and name who did it (in main menu)
    }


}