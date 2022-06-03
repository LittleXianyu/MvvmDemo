package com.example.mvvmdemo.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemo.databinding.InfoitemBinding

class InfoListAdapter : ListAdapter<ItemModel, InfoListAdapter.InfoViewHolder>(InfoDiffCallback()) {
    class InfoViewHolder(
        private val binding: InfoitemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
        }
        fun setData(itemModel: ItemModel, bgColor: Int) {
            binding.model = itemModel
            binding.itemLayout.setBackgroundColor(bgColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        return InfoViewHolder(
            InfoitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val model = getItem(position)
        holder.setData(model, if (position % 2 == 1) Color.GREEN else Color.RED)
    }
}

private class InfoDiffCallback : DiffUtil.ItemCallback<ItemModel>() {

    override fun areItemsTheSame(
        oldItem: ItemModel,
        newItem: ItemModel
    ): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(
        oldItem: ItemModel,
        newItem: ItemModel
    ): Boolean {
        return oldItem.key == newItem.key
    }
}
