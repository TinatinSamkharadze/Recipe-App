import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Recipe

class AddRecipeDialog(private val onRecipeAdded: (Recipe) -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_recipe, null)

        val titleInput = view.findViewById<EditText>(R.id.etTitle)
        val descInput = view.findViewById<EditText>(R.id.etDescription)

        builder.setView(view)
            .setTitle("Add Recipe")
            .setPositiveButton("Add") { _, _ ->
                val recipe = Recipe(
                    title = titleInput.text.toString(),
                    description = descInput.text.toString(),
                    imageUrl = ""
                )
                onRecipeAdded(recipe)
            }
            .setNegativeButton("Cancel", null)

        return builder.create()
    }
}
