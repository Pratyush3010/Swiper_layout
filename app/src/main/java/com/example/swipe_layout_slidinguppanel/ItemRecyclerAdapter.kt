package com.example.swipe_layout_slidinguppanel

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemRecyclerAdapter(private val list: List<String>,private val context:Context):RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>() {


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
   return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentItem = list[position]
        holder.itemtext.text = currentItem
    }


    class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
  val itemtext:TextView = itemview.findViewById(R.id.textitem)
    }
}