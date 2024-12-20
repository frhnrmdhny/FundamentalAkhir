package com.example.eventdicoding.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentUpcomingBinding
import com.example.eventdicoding.ui.detail.DetailActivity
import com.example.eventdicoding.ui.EventAdapter
import com.google.android.material.snackbar.Snackbar

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private lateinit var upcomingViewModel: UpcomingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        upcomingViewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)

        binding.rvUpcoming.layoutManager = LinearLayoutManager(context)

        upcomingViewModel.getEvents().observe(viewLifecycleOwner) { eventList ->
            binding.progressBar.visibility = View.GONE

            if (eventList != null && eventList.isNotEmpty()) {
                val adapter = EventAdapter(eventList) { event ->
                    val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                        putExtra("id", event.id)
                    }
                    startActivity(intent)
                }
                binding.rvUpcoming.adapter = adapter
            } else {
                binding.rvUpcoming.adapter = null
            }
        }

        upcomingViewModel.getErrorMessage().observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null && errorMessage.isNotEmpty()) {
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            }
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        upcomingViewModel.fetchUpcomingEvents(forceReload = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
