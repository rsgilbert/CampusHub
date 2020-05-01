package com.monstercode.campushub.orderlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.monstercode.campushub.databinding.OrderListItemBinding
import com.monstercode.campushub.domain.Order

class OrderListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Order, OrderListAdapter.ViewHolder>(OrderDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order: Order = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(order)
        }
        holder.bind(order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    class ViewHolder private constructor(private val binding: OrderListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Order) {
            binding.order = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OrderListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (order: Order) -> Unit) {
        fun onClick(order: Order) = clickListener(order)
    }
}

class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem._id == newItem._id

    override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
}
