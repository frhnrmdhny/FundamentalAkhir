package com.example.eventdicoding.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventdicoding.data.database.FavoriteEvent
import com.example.eventdicoding.databinding.EventFavItemBinding

class FavoriteAdapter(
    private val onItemClicked: (FavoriteEvent) -> Unit,
) : ListAdapter<FavoriteEvent, FavoriteAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: EventFavItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: FavoriteEvent, onItemClicked: (FavoriteEvent) -> Unit) {
            binding.favEventTitle.text = event.name
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.favEventImage)
            binding.root.setOnClickListener {
                onItemClicked(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventFavItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onItemClicked)
    }
}

class DiffCallback : DiffUtil.ItemCallback<FavoriteEvent>() {
    override fun areItemsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent): Boolean {
        return oldItem == newItem
    }
}



