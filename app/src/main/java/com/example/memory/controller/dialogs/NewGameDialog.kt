package com.example.memory.controller.dialogs

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
import com.example.memory.R
import com.example.memory.controller.GameActivity
import com.example.memory.modell.MainModel
import kotlinx.android.synthetic.main.new_game_dialog.*

/**
 * NewGameDialog showing up after pressing "play now" button in main menu
 */
class NewGameDialog(c: Activity) : Dialog(c), AdapterView.OnItemSelectedListener {

    init {
        setCancelable(true)
    }

    private var size = MainModel.SMALL
    private val tag = "NewGameDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.new_game_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        spinner!!.onItemSelectedListener = this

        // adapter for spinner, provides the data to be displayed
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, MainModel.listOfSizes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        cancel.setOnClickListener {
            dismiss()
        }
        start.setOnClickListener {
            Log.i(tag, "Selected item is: $size")
            // start game with intent and provide size as extra data
            context.startActivity(Intent(context, GameActivity::class.java).putExtra("SIZE", size))
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> size = MainModel.SMALL
            1 -> size = MainModel.MEDIUM
            2 -> size = MainModel.LARGE
            else -> Log.e(tag, "Not an available selection!")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // not used
    }


}