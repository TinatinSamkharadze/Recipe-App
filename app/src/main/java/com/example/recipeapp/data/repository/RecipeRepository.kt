package com.example.recipeapp.data.repository


import com.example.recipeapp.data.db.RecipeDao
import com.example.recipeapp.data.model.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {

    // აიღე ყველა რეცეპტი
    suspend fun getAllRecipes(): List<Recipe> {
        return recipeDao.getAllRecipes()
    }

    // დაამატე ახალი რეცეპტი
    suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun getFavoriteRecipes(): List<Recipe> {
        return recipeDao.getFavoriteRecipes()
    }


}
