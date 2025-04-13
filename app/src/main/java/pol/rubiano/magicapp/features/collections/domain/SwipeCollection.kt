package pol.rubiano.magicapp.features.collections.domain

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.features.collections.presentation.CollectionsAdapter

/**
 * Callback para gestionar acciones de swipe en el RecyclerView.
 * Se delega la acción al componente que implemente la interfaz SwipeAdapterActions.
 */
class SwipeToActionCallback(
    private val adapterActions: SwipeAdapterActions
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    interface SwipeAdapterActions {
        fun removeItem(position: Int)
        fun archiveItem(position: Int)
    }

    // Se deshabilita el swipe para el item “nuevo” comprobando el tipo de ViewHolder.
    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return if (viewHolder is CollectionsAdapter.NewCollectionViewHolder)
            0
        else
            super.getSwipeDirs(recyclerView, viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        when (direction) {
            ItemTouchHelper.LEFT  -> adapterActions.removeItem(position)
            ItemTouchHelper.RIGHT -> adapterActions.archiveItem(position)
        }
    }

    // Si deseas personalizar la animación durante el swipe, puedes modificar este método.
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
