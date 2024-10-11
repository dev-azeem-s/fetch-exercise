package com.example.fetchexcercise.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchexcercise.R
import com.example.fetchexcercise.data.HiringItem
import com.example.fetchexcercise.databinding.GroupLayoutBinding

class GroupedItemAdapter(private val groupedItems: Map<Int, List<HiringItem>>, private val context: Context) :
    RecyclerView.Adapter<GroupedItemAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(val binding: GroupLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = GroupLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {

        val listId = groupedItems.keys.elementAt(position)
        holder.binding.tvListIdGroup.text = "${getString(context ,R.string.list_id)} $listId"

        val items = groupedItems[listId] ?: emptyList()
        val itemAdapter = ItemAdapter(items)
        holder.binding.recyclerViewItems.adapter = itemAdapter
        holder.binding.recyclerViewItems.layoutManager = LinearLayoutManager(holder.itemView.context)
    }

    override fun getItemCount(): Int = groupedItems.size
}

