// ui/main/AddRecipeDialog.kt
package com.example.recipeapp.ui.main

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.recipeapp.databinding.DialogAddRecipeBinding
import com.example.recipeapp.ui.adapter.RecipeAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class AddRecipeDialog : DialogFragment() {

    private lateinit var binding: DialogAddRecipeBinding
    private var onAddRecipe: ((title: String, description: String, imageUrl: String) -> Unit)? = null

    fun setOnAddRecipeListener(listener: (title: String, description: String, imageUrl: String) -> Unit) {
        onAddRecipe = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddRecipeBinding.inflate(requireActivity().layoutInflater)

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle("Add New Recipe")
            .setPositiveButton("Add") { _, _ ->
                val title = binding.titleEditText.text.toString()
                val description = binding.descriptionEditText.text.toString()
                val imageUrl = binding.imageUrlEditText.text.toString()

                if (title.isNotBlank() && description.isNotBlank()) {
                    onAddRecipe?.invoke(title, description, imageUrl)
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
    }
}

