
// data/repository/RecipeRepository.kt
package com.example.recipeapp.data.repository

import com.example.recipeapp.data.db.RecipeDao
import com.example.recipeapp.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao
) {
    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()


    // Change this to return Flow<List<Recipe>>
    fun getFavoriteRecipes(): Flow<List<Recipe>> = recipeDao.getFavoriteRecipes()

    fun getRecipeById(id: Int): Flow<Recipe?> = recipeDao.getRecipeById(id)


    suspend fun insertRecipe(recipe: Recipe) = recipeDao.insertRecipe(recipe)

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) =
        recipeDao.updateFavoriteStatus(id, isFavorite)
}

