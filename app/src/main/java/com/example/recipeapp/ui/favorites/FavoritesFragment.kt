// ui/favorites/FavoritesFragment.kt
package com.example.recipeapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.ui.adapter.RecipeAdapter
import com.example.recipeapp.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeFavorites()
    }

    private fun initRecyclerView() {
        adapter = RecipeAdapter { recipeId ->
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToRecipeDetailsFragment(recipeId)
            findNavController().navigate(action)
        }

        binding.favoritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoritesFragment.adapter
        }
    }

    private fun observeFavorites() {
        lifecycleScope.launch {
            viewModel.favoriteRecipes.collect { recipes ->
                adapter.submitList(recipes)
                binding.emptyState.visibility = if (recipes.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
