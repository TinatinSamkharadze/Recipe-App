package com.example.recipeapp.ui.home

import AddRecipeDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.ui.adapter.RecipeAdapter
import com.example.recipeapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecipeAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Initialize adapter with click listener for navigation
        adapter = RecipeAdapter(emptyList()) { recipe ->
            val bundle = Bundle().apply {
                putString("recipe_title", recipe.title)
                putString("recipe_description", recipe.description)
                putString("recipe_image", recipe.imageUrl)
                putInt("recipe_id", recipe.id)
            }
            findNavController().navigate(R.id.action_home_to_details, bundle)
        }

        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipes.adapter = adapter

        // Observe recipes
        viewModel.getRecipes().observe(viewLifecycleOwner) { recipes ->
            adapter.updateData(recipes)
        }

        // Add Recipe button - opens dialog
        binding.btnAddRecipe.setOnClickListener {
            AddRecipeDialog { recipe ->
                viewModel.addRecipe(recipe)
            }.show(parentFragmentManager, "AddRecipeDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}