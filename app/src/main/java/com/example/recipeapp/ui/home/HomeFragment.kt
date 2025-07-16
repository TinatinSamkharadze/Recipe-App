// ui/home/HomeFragment.kt
package com.example.recipeapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.ui.adapter.RecipeAdapter
import com.example.recipeapp.ui.main.AddRecipeDialog
import com.example.recipeapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var trendingAdapter: RecipeAdapter
    private lateinit var allRecipesAdapter: RecipeAdapter
    // You might need category adapter too if you have categories

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViews()
        observeRecipes()
        initListeners()
    }

    private fun initRecyclerViews() {
        // Trending recipes adapter (horizontal)
        trendingAdapter = RecipeAdapter { recipeId ->
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailsFragment(recipeId)
            findNavController().navigate(action)
        }

        // All recipes adapter (vertical)
        allRecipesAdapter = RecipeAdapter { recipeId ->
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailsFragment(recipeId)
            findNavController().navigate(action)
        }

        // Setup trending recipes RecyclerView (horizontal)
        binding.rvTrendingRecipes.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }

        // Setup all recipes RecyclerView (vertical)
        binding.rvAllRecipes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allRecipesAdapter
        }

        // Setup categories RecyclerView (grid) - if you have a category adapter
        // binding.rvCategories.apply {
        //     layoutManager = GridLayoutManager(requireContext(), 2)
        //     adapter = categoryAdapter
        // }
    }

    private fun observeRecipes() {
        lifecycleScope.launch {
            viewModel.recipes.collect { recipes ->
                // Update both adapters with the same data
                // You might want to filter or separate trending vs all recipes
                trendingAdapter.submitList(recipes.take(5)) // Show first 5 as trending
                allRecipesAdapter.submitList(recipes)

                // Show/hide empty state
                binding.emptyState.visibility = if (recipes.isEmpty()) View.VISIBLE else View.GONE
                binding.scrollContent.visibility = if (recipes.isEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    private fun initListeners() {
        // Search functionality
        binding.etSearch.setOnEditorActionListener { _, _, _ ->
            val query = binding.etSearch.text.toString().trim()
            viewModel.searchRecipes(query)
            false
        }

        // Real-time search as user types
        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val query = s.toString().trim()
                viewModel.searchRecipes(query)
            }
        })

        // See all trending recipes
        binding.tvSeeAll.setOnClickListener {
            // Navigate to all recipes or trending recipes screen
            // findNavController().navigate(HomeFragmentDirections.actionToAllRecipesFragment())
        }

        // If you have a FAB for adding recipes, you might need to add it back to your layout
        // Or handle it differently based on your new design
        // binding.addRecipeFab.setOnClickListener {
        //     AddRecipeDialog().show(childFragmentManager, "AddRecipeDialog")
        // }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}