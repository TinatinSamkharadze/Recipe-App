package com.example.recipeapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): Flow<Recipe?>

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean): Int
}
