package com.example.eventdicoding.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.eventdicoding.R
import com.example.eventdicoding.data.response.Event
import com.example.eventdicoding.databinding.ActivityDetailBinding
import com.example.eventdicoding.di.Injection
import com.example.eventdicoding.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra("id", 0)
        Log.d("DetailActivity", "Event ID: $eventId")

        val factory = ViewModelFactory(Injection.provideRepository(application))
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)

        if (eventId != 0) {
            viewModel.fetchEventDetail(eventId)
        } else {
            Log.e("DetailActivity", "Invalid Event ID")
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.event.observe(this) { event ->
            if (event != null) {
                displayEventDetails(event)
                setupFavoriteButton(event)
            } else {
                Log.e("DetailActivity", "Event data is null")
            }
        }
    }

    private fun displayEventDetails(event: Event) {
        binding.eventTitle.text = event.name
        binding.eventOrganizer.text = event.ownerName
        binding.eventCity.text = event.cityName
        binding.eventTime.text = "Time: ${event.beginTime ?: "N/A"}"
        binding.eventQuota.text =
            "Sisa Quota: ${(event.quota ?: 0) - (event.registrants ?: 0)}"
        binding.eventDescription.text = HtmlCompat.fromHtml(
            event.description.orEmpty(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        Glide.with(this).load(event.mediaCover).into(binding.eventImage)

        binding.btnRegister.setOnClickListener {
            val eventLink = event.link ?: "https://www.dicoding.com/events/8722"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(eventLink)))
        }
    }

    private fun setupFavoriteButton(event: Event) {
        viewModel.isFavorite.observe(this) { isFavorite ->
            val icon =
                if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            binding.floatingActionButton.setImageResource(icon)
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

