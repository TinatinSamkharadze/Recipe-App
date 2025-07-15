package com.example.recipeapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.databinding.FragmentRecipeDetailsBinding
import com.example.recipeapp.viewmodel.RecipeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {

    private val args: RecipeDetailsFragmentArgs by navArgs()
    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadRecipe(args.recipeId)
        observeRecipe()
        initListeners()
    }

    private fun observeRecipe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipe.collect { recipe ->
                    recipe?.let { bindRecipe(it) }
                }
            }
        }
    }

    private fun bindRecipe(recipe: Recipe) {
        Glide.with(binding.root)
            .load(recipe.image)
            .into(binding.recipeImage)

        binding.titleText.text = recipe.title
        binding.descriptionText.text = recipe.description

        // Update favorite button state
        binding.favoriteButton.apply {
            if (recipe.isFavorite) {
                setIconResource(R.drawable.ic_favorite_filled)
            } else {
                setIconResource(R.drawable.ic_favorite_border)
            }
        }
    }

    private fun initListeners() {
        binding.favoriteButton.setOnClickListener {
            viewModel.toggleFavoriteStatus()
        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
