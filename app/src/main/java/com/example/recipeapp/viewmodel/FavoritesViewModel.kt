// viewmodel/FavoritesViewModel.kt
package com.example.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _favoriteRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val favoriteRecipes: StateFlow<List<Recipe>> = _favoriteRecipes

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoriteRecipes().collect { recipes ->
                _favoriteRecipes.value = recipes
            }
        }
    }
}
