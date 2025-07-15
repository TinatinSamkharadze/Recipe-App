// viewmodel/RecipeDetailsViewModel.kt
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
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe

    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            repository.getRecipeById(id).collect { recipe ->
                _recipe.value = recipe
            }
        }
    }

    fun toggleFavoriteStatus() {
        val currentRecipe = _recipe.value ?: return
        viewModelScope.launch {
            repository.updateFavoriteStatus(
                currentRecipe.id,
                !currentRecipe.isFavorite
            )
        }
    }
}
