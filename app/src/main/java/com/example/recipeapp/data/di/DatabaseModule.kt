package com.example.recipeapp.data.di

import android.app.Application
import androidx.room.Room
import com.example.recipeapp.data.db.AppDatabase
import com.example.recipeapp.data.db.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "recipe_db").build()
    }

    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao {
        return db.recipeDao()
    }
}
