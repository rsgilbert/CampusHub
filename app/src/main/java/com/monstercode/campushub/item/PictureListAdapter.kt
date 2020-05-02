package com.monstercode.campushub.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.monstercode.campushub.databinding.PictureListItemBinding
import com.monstercode.campushub.domain.Picture

class PictureListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Picture, PictureListAdapter.ViewHolder>(PictureDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Picture = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    class ViewHolder private constructor(private val binding: PictureListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Picture) {
            binding.picture = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PictureListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (item: Picture) -> Unit) {
        fun onClick(item: Picture) = clickListener(item)
    }
}

class PictureDiffCallback : DiffUtil.ItemCallback<Picture>() {
    override fun areItemsTheSame(oldPicture: Picture, newPicture: Picture) =
        oldPicture._id == newPicture._id

    override fun areContentsTheSame(oldPicture: Picture, newPicture: Picture) =
        oldPicture == newPicture
}
