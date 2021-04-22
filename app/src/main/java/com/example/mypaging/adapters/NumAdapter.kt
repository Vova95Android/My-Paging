package com.example.mypaging.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypaging.data.NumData
import com.example.mypaging.databinding.NumItemBinding

class NumAdapter(private val listener: OnClickListener, private val change: OnChangeListener) :
    ListAdapter<NumData, NumAdapter.NumPropertyViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<NumData>() {
        override fun areItemsTheSame(oldItem: NumData, newItem: NumData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NumData, newItem: NumData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumPropertyViewHolder {
        return NumPropertyViewHolder(NumItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NumPropertyViewHolder, position: Int) {
        val num = getItem(position)
        change.onChange(position)
        holder.bind(num, listener)
    }

    class OnClickListener(val clickListener: (num: NumData) -> Unit) {
        fun onClick(num: NumData) = clickListener(num)
    }

    class OnChangeListener(val changeListener: (position: Int) -> Unit) {
        fun onChange(position: Int) = changeListener(position)
    }

    class NumPropertyViewHolder(private var binding: NumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NumData, listener: OnClickListener) {
            binding.executePendingBindings()
            binding.textNum.text = "Randoms num is ${data.num}"
            itemView.setOnClickListener { listener.onClick(data) }
        }
    }
}