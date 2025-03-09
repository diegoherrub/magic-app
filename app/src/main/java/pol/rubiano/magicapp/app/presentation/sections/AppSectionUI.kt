package pol.rubiano.magicapp.app.presentation.sections

import android.content.Context

class AppSectionUI(
    private val image: Int,
    private val title: String,
    private val description: String,
    private val context: Context
) {
    fun getSectionImage(): Int = image
    fun getSectionTitle(): String = title
    fun getSectionDescription(): String = description
}