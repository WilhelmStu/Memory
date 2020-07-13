package com.example.memory.controller

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.memory.R
import com.example.memory.modell.MainModel
import java.lang.IllegalArgumentException


class CustomAdapter(private val arr: IntArray, private val listener: CustomOnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE1 = 0
        private const val TYPE2 = 1
    }

    override fun getItemViewType(position: Int) =
        when (arr.size == MainModel.SMALL || arr.size == MainModel.MEDIUM) {
            true -> TYPE1
            false -> TYPE2
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {

        TYPE1 -> InViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        )
        TYPE2 -> OutViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)
        )
        else -> throw IllegalArgumentException()
    }


    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            TYPE1 -> bindItems(holder, arr[position])
            TYPE2 -> bindSecondItems(holder, arr[position])
            else -> throw  IllegalArgumentException()
        }

    private fun bindItems(holder: RecyclerView.ViewHolder, int: Int) {
        val inViewHolder = holder as InViewHolder
        inViewHolder.gridItem.setOnClickListener {
            listener.onCardClicked(int, inViewHolder.gridItem)
        }
    // todo clean up code, UI and rename functions!!
    }

    private fun bindSecondItems(holder: RecyclerView.ViewHolder, int: Int) {
        val outViewHolder = holder as OutViewHolder
        outViewHolder.gridItem.setOnClickListener {
            listener.onCardClicked(int, outViewHolder.gridItem)
        }


    }


    class InViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gridItem: ImageView = itemView.findViewById(R.id.gridItem)
    }

    class OutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gridItem: ImageView = itemView.findViewById(R.id.gridItem2)
    }


}
