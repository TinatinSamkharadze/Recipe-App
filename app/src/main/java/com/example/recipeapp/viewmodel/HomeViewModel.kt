// viewmodel/HomeViewModel.kt
package com.example.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _allRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            repository.getAllRecipes().collect { recipes ->
                _allRecipes.value = recipes
                _recipes.value = recipes
            }
        }
    }

    fun searchRecipes(query: String) {
        _isSearching.value = true

        if (query.isBlank()) {
            // If query is empty, show all recipes
            _recipes.value = _allRecipes.value
        } else {
            // Filter recipes based on title and description
            val filteredRecipes = _allRecipes.value.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true) ||
                        recipe.description.contains(query, ignoreCase = true)
            }
            _recipes.value = filteredRecipes
        }

        _isSearching.value = false
    }

    fun clearSearch() {
        _recipes.value = _allRecipes.value
    }

    fun addRecipe(title: String, description: String, imageUrl: String) {
        val recipe = Recipe(
            id = (_allRecipes.value.maxByOrNull { it.id }?.id ?: 0) + 1,
            title = title,
            description = description,
            image = imageUrl.ifBlank { "https://placehold.co/600x400" }
        )

        viewModelScope.launch {
            repository.insertRecipe(recipe)
        }
    }

    // Optional: Get trending recipes (first 5 or most recent)
    fun getTrendingRecipes(): List<Recipe> {
        return _allRecipes.value.take(5)
    }

    // Optional: Get recipes by category if you implement categories
    fun getRecipesByCategory(category: String): List<Recipe> {
        return _allRecipes.value.filter { recipe ->
            // Assuming you add a category field to your Recipe model
            // recipe.category == category
            true // Placeholder for now
        }
    }
}