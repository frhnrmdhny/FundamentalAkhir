package com.example.eventdicoding.ui.finish

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventdicoding.databinding.FragmentFinishedBinding
import com.example.eventdicoding.ui.detail.DetailActivity
import com.example.eventdicoding.ui.EventAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class FinishFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private lateinit var finishViewModel: FinishViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        finishViewModel = ViewModelProvider(this).get(FinishViewModel::class.java)

        binding.rvFinished.layoutManager = GridLayoutManager(context,2, RecyclerView.VERTICAL,false)

        finishViewModel.getEvents().observe(viewLifecycleOwner) { eventList ->
            binding.progressBar.visibility = View.GONE

            if (eventList != null && eventList.isNotEmpty()) {
                val adapter = EventAdapter(eventList) { event ->
                    val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                        putExtra("id", event.id)
                    }
                    startActivity(intent)
                }
                binding.rvFinished.adapter = adapter
            } else {
                binding.rvFinished.adapter = null
            }
        }

        finishViewModel.getErrorMessage().observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null && errorMessage.isNotEmpty()) {
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            }
        }

        if (finishViewModel.getEvents().value == null) {
            binding.progressBar.visibility = View.VISIBLE
            finishViewModel.fetchFinishedEvents()
        }

        
        return root
    }


    override fun onResume() {
        super.onResume()
        finishViewModel.fetchFinishedEvents(forceReload = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
