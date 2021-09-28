package com.tutorials.eu.favdish.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tutorials.eu.favdish.databinding.ItemCustomListBinding

class CustomListItemAdapter(
    private val activity: Activity,
    private val listitems:List<String>,
    private val selection:String):RecyclerView.Adapter<CustomListItemAdapter.ViewHolder>() {

        class ViewHolder(view: ItemCustomListBinding):RecyclerView.ViewHolder(view.root){
            val tvText=view.tvText
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemCustomListBinding= ItemCustomListBinding.
        inflate(LayoutInflater.from(activity),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=listitems[position]
        holder.tvText.text=item

    }

    override fun getItemCount(): Int {
        return  listitems.size
    }

}