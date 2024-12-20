package com.example.eventdicoding.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventdicoding.R
import com.example.eventdicoding.data.response.Event
import com.example.eventdicoding.data.response.ListEventsItem

class EventAdapter(
    private val events: List<ListEventsItem>,
    private val onItemClicked: (ListEventsItem) -> Unit,
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventTitle: TextView = itemView.findViewById(R.id.event_title)
        val eventImage: ImageView = itemView.findViewById(R.id.event_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item_layout, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.eventTitle.text = event.name
        Glide.with(holder.itemView.context)
            .load(event.mediaCover)
            .into(holder.eventImage)

        holder.itemView.setOnClickListener {
            onItemClicked(event)
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
        }

        const val EXTRA_EVENT = "extra_event"
    }
}
