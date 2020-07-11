package com.example.memory

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.new_game_dialog.*


class NewGameDialog(var c: Activity) : Dialog(c), AdapterView.OnItemSelectedListener {

    init {
        setCancelable(true)
    }
    private var size = 16;
    private val tag = "NewGameDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.new_game_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        val listOfSizes = arrayOf("16 Tiles", "24 Tiles", "32 Tiles")
        spinner!!.onItemSelectedListener = this

        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, listOfSizes)

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        cancel.setOnClickListener {
            dismiss()
        }
        start.setOnClickListener {
            Log.i(tag, "Selected item is: $size")
            context.startActivity(Intent(context, GameActivity::class.java).putExtra("SIZE", size))
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> size = 16
            1 -> size = 24
            2 -> size = 32
            else -> Log.e(tag, "Not an available selection!")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }



}