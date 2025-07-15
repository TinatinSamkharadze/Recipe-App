// data/model/Recipe.kt
package com.example.recipeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val isFavorite: Boolean = false
)
