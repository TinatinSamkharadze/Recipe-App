package com.example.recipeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.databinding.ItemRecipeBinding

class RecipeAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.root.setOnClickListener { onClick(recipe.id) }

            // Use the correct binding properties from your XML
            binding.tvRecipeTitle.text = recipe.title
            binding.tvRecipeDescription.text = recipe.description

            // Load image using Glide
            Glide.with(binding.root)
                .load(recipe.image)
                .centerCrop()
                .into(binding.ivRecipeImage)

            // If your Recipe model has cooking time and rating, you can also bind them:
            // binding.tvCookingTime.text = recipe.cookingTime
            // binding.tvRating.text = recipe.rating.toString()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }
}