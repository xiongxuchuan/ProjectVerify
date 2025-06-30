package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bean.WaterfallItem

class WaterfallAdapter(private val items: List<WaterfallItem>) :
    RecyclerView.Adapter<WaterfallAdapter.WaterfallViewHolder>() {

    class WaterfallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.itemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterfallViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_waterfall, parent, false)
        return WaterfallViewHolder(view)
    }

    override fun onBindViewHolder(holder: WaterfallViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.title

        // 设置高度
        val layoutParams = holder.itemView.layoutParams
        if (item.isDoubleHeight) {
            // 如果是双倍高度条目，设置高度为两倍
            layoutParams.height = holder.itemView.context.resources.getDimensionPixelSize(R.dimen.double_item_height)
        } else {
            // 普通高度
            layoutParams.height = holder.itemView.context.resources.getDimensionPixelSize(R.dimen.normal_item_height)
        }
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int = items.size
}