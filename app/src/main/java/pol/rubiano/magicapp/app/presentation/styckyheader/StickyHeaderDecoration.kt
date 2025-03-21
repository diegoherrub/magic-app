package pol.rubiano.magicapp.app.presentation.styckyheader

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.features.presentation.adapters.GlossaryAdapter
import androidx.core.graphics.withSave
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.domain.GlossaryItem

class StickyHeaderDecoration(
    private val adapter: GlossaryAdapter
) : RecyclerView.ItemDecoration(){

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val topChild = parent.getChildAt(0) ?: return
        val topChildPos = parent.getChildAdapterPosition(topChild)
        if (topChildPos == RecyclerView.NO_POSITION) return

        // Encuentra la posición de la cabecera correspondiente
        val headerPosition = findHeaderPositionForItem(topChildPos)
        if (headerPosition == -1) return

        // Infla y bindea la vista de cabecera
        val headerView = getHeaderViewForItem(headerPosition, parent)
        fixLayoutSize(parent, headerView)

        val contactPoint = headerView.bottom
        val childInContact = getChildInContact(parent, contactPoint)

        canvas.withSave {
            if (childInContact != null && adapter.getItemViewType(
                    parent.getChildAdapterPosition(
                        childInContact
                    )
                ) == GlossaryAdapter.VIEW_TYPE_HEADER
            ) {
                val offset = childInContact.top - headerView.height
                translate(0f, offset.toFloat())
            }
            headerView.draw(this)
        }
    }

    // Busca la cabecera recorriendo hacia atrás desde la posición actual
    private fun findHeaderPositionForItem(itemPosition: Int): Int {
        for (position in itemPosition downTo 0) {
            if (adapter.getItemViewType(position) == GlossaryAdapter.VIEW_TYPE_HEADER) {
                return position
            }
        }
        return -1
    }

    // Infla la vista de cabecera y bindea el dato (por ejemplo, la categoría)
    private fun getHeaderViewForItem(headerPosition: Int, parent: RecyclerView): View {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerView = layoutInflater.inflate(R.layout.glossary_item_header_view, parent, false)
        val headerItem = (adapter.getItem(headerPosition) as GlossaryItem.Header)
        headerView.findViewById<TextView>(R.id.title_sticky_header).text = headerItem.category
        return headerView
    }

    // Busca el hijo que está en el punto de contacto (para saber cuándo deslizar la cabecera)
    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.top in 1..contactPoint) {
                return child
            }
        }
        return null
    }

    // Mide y posiciona la vista de cabecera para que se dibuje correctamente
    private fun fixLayoutSize(parent: RecyclerView, view: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        val childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)

        view.measure(childWidth, childHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

}