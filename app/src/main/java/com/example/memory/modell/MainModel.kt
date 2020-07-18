package com.example.memory.modell

import com.example.memory.R


object MainModel {


    const val SMALL = 16
    const val MEDIUM = 30
    const val LARGE = 42
    const val COLUMN_SMALL = 4
    const val COLUMN_MEDIUM = 5
    const val COLUMN_LARGE = 6
    const val MEMORY_BLUE = R.drawable.memory_with_shadow_blue
    const val MEMORY_RED = R.drawable.memory_with_shadow
    const val PLAY_BUTTON_BLUE = R.drawable.play_now_blue_v2
    const val PLAY_BUTTON_RED = R.drawable.play_now_v2

    val listOfSizes = arrayOf("$SMALL Tiles", "$MEDIUM Tiles", "$LARGE Tiles")
    const val card_back_texture = R.drawable.memory_card_back_v1
    var defaultColor = true
    val cardTextures = arrayOf(
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_3,
        R.drawable.card_4,
        R.drawable.card_5,
        R.drawable.card_6,
        R.drawable.card_7,
        R.drawable.card_8,
        R.drawable.card_9,
        R.drawable.card_10,
        R.drawable.card_11,
        R.drawable.card_12,
        R.drawable.card_13,
        R.drawable.card_14,
        R.drawable.card_15,
        R.drawable.card_16,
        R.drawable.card_17,
        R.drawable.card_18,
        R.drawable.card_19,
        R.drawable.card_20,
        R.drawable.card_21
    )
}