package com.example.eventdicoding.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentHomeBinding
import com.example.eventdicoding.ui.detail.DetailActivity
import com.example.eventdicoding.ui.EventAdapter
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.rvHome.layoutManager = LinearLayoutManager(context)

        homeViewModel.getEvents().observe(viewLifecycleOwner) { eventList ->
            binding.progressBar.visibility = View.GONE

            if (eventList != null && eventList.isNotEmpty()) {
                val adapter = EventAdapter(eventList) { event ->
                    val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                        putExtra("id", event.id)
                    }
                    startActivity(intent)
                }
                binding.rvHome.adapter = adapter
            } else {
                binding.rvHome.adapter = null
            }
        }

        homeViewModel.getErrorMessage().observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null && errorMessage.isNotEmpty()) {
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchUpcomingEvents(forceReload = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}