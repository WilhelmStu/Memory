package com.example.memory.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.memory.R
import com.example.memory.modell.MainModel
import java.lang.IllegalArgumentException

/**
 * Adapter class for the recyclerView, that gives the correct layout for the card, based on the selected game size
 * Also sets the listener for cards being clicked, handled in GameActivity
 */
class CustomAdapter(private val arr: IntArray, private val listener: CustomOnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE1 = 0
        private const val TYPE2 = 1
        private const val TYPE3 = 2
    }

    override fun getItemViewType(position: Int) =
        when (arr.size) {
            MainModel.SMALL -> TYPE1
            MainModel.MEDIUM -> TYPE2
            MainModel.LARGE -> TYPE3
            else -> TYPE1
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {

        TYPE1 -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_item_small, parent, false))
        TYPE2 -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_item_medium, parent, false))
        TYPE3 -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_item_large, parent, false))
        else -> throw IllegalArgumentException()
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.gridItem.setOnClickListener {
            listener.onCardClicked(position, holder.gridItem)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gridItem: ImageView = itemView.findViewById(R.id.gridItem)
    }

}
