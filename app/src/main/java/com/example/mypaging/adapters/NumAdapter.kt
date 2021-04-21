package com.example.mypaging.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypaging.data.NumData
import com.example.mypaging.databinding.NumItemBinding
import com.example.mypaging.paging.MyPagingSorce

class NumAdapter(val listener: OnClickListener, val change: OnChangeListener) : ListAdapter<NumData, NumAdapter.NumPropertyViewHolder>(DiffCallback()) {

    private var listData: List<NumData> = listOf()
    var activeLastPosition=0

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
        if (position>activeLastPosition) {
            activeLastPosition = position
            change.onChange(activeLastPosition)
        }
        holder.bind(num, listener, activeLastPosition)
    }

    class OnClickListener(val clickListener: (num: NumData) -> Unit) {
        fun onClick(num: NumData) = clickListener(num)
    }

    class OnChangeListener(val changeListener: (position: Int) -> Unit) {
        fun onChange(position: Int) = changeListener(position)
    }

    override fun submitList(list: List<NumData>?) {
        super.submitList(list)
        if (list != null) {
            listData=list
        }

    }

    class NumPropertyViewHolder(private var binding: NumItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NumData, listener: OnClickListener, activeLastPosition: Int) {
            binding.executePendingBindings()
            binding.textNum.text="Rundom num is ${data.num}"
            itemView.setOnClickListener { listener.onClick(data) }
        }
    }
}