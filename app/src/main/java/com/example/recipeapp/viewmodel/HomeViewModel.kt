package com.example.recipeapp.viewmodel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recipeapp.data.db.AppDatabase
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository

    init {
        val recipeDao = AppDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(recipeDao)
    }

    fun addRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.insertRecipe(recipe)
    }

    fun getFavorites(): LiveData<List<Recipe>> = liveData {
        emit(repository.getFavoriteRecipes())
    }

    fun getRecipes(): LiveData<List<Recipe>> = liveData {
        emit(repository.getAllRecipes())
    }
}



