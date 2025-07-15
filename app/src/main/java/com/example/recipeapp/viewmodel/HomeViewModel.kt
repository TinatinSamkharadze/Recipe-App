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

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            repository.getAllRecipes().collect { recipes ->
                _recipes.value = recipes
            }
        }
    }

    fun addRecipe(title: String, description: String, imageUrl: String) {
        val recipe = Recipe(
            id = (_recipes.value.maxByOrNull { it.id }?.id ?: 0) + 1,
            title = title,
            description = description,
            image = imageUrl.ifBlank { "https://placehold.co/600x400" }
        )
        viewModelScope.launch {
            repository.insertRecipe(recipe)
        }
    }
}
