package com.example.eventdicoding.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentFavoriteBinding
import com.example.eventdicoding.di.Injection
import com.example.eventdicoding.ui.ViewModelFactory

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val factory = ViewModelFactory(Injection.provideRepository(requireContext()))
        viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)


        adapter = FavoriteAdapter()
        binding.rvFavorite.layoutManager = LinearLayoutManager(context)
        binding.rvFavorite.adapter = adapter

        viewModel.getAllFavorites().observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)
            binding.progressBar.visibility = if (favorites.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}

