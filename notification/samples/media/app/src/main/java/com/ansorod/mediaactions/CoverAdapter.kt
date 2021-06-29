package com.ansorod.mediaactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CoverAdapter(private val onClick: (Int) -> Unit, private val data: List<Int>): RecyclerView.Adapter<CoverAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cover, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
        holder.coverView.setImageResource(current)
        holder.coverView.setOnClickListener {
            onClick(current)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val coverView = view.findViewById<ImageView>(R.id.coverImageView)
    }
}