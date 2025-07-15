// data/db/AppDatabase.kt
package com.example.recipeapp.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeapp.data.model.Recipe


@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
