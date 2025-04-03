package pol.rubiano.magicapp.app.presentation

import androidx.recyclerview.widget.DiffUtil

class AppDiffUtil<T: Any>(
    private val itemSame: (T, T) -> Boolean = { old, new -> old == new },
    private val contentSame: (T, T) -> Boolean = { old, new -> old == new }
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = itemSame(oldItem, newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T) = contentSame(oldItem, newItem)
}