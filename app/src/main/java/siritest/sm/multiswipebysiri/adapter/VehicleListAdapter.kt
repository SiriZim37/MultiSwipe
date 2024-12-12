package siritest.sm.multiswipebysiri.adapter

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat
import siritest.sm.multiswipebysiri.R
import siritest.sm.multiswipebysiri.data.model.Vehicle
import siritest.sm.multiswipebysiri.util.Logger

class VehicleListAdapter(dataSet: List<Vehicle> = emptyList())
    : SwipeAdapter<Vehicle, VehicleListAdapter.ViewHolder>(dataSet) {

    class ViewHolder(iceCreamLayout: View) : SwipeAdapter.ViewHolder(iceCreamLayout) {
        val iceCreamNameView: TextView = itemView.findViewById(R.id.ice_cream_name)
        val iceCreamPriceView: TextView = itemView.findViewById(R.id.ice_cream_price)
        val dragIcon: AppCompatImageView = itemView.findViewById(R.id.drag_icon)
        val iceCreamIcon: AppCompatImageView? = itemView.findViewById(R.id.ice_cream_icon)
        val iceCreamPhotoFilter: View? = itemView.findViewById(R.id.ice_cream_photo_filter)
    }

    override fun getViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(item: Vehicle, viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context

        // Set vehicle name and price
        viewHolder.iceCreamNameView.text = item.name
        viewHolder.iceCreamPriceView.text = context.getString(R.string.priceFormat, item.price)

//        // Set the icon/image color
//        if (viewHolder.iceCreamIcon != null) {
//            val iceCreamIconColor = Color.rgb(red, green, blue)
//            ImageViewCompat.setImageTintList(viewHolder.iceCreamIcon, ColorStateList.valueOf(iceCreamIconColor))
//        } else if (viewHolder.iceCreamPhotoFilter != null) {
//            val iceCreamPhotoFilter = Color.argb(128, red, green, blue)
//            viewHolder.iceCreamPhotoFilter.setBackgroundColor(iceCreamPhotoFilter)
//        }
    }

    override fun getViewToTouchToStartDraggingItem(item: Vehicle, viewHolder: ViewHolder, position: Int) = viewHolder.dragIcon

    override fun onDragStarted(item: Vehicle, viewHolder: ViewHolder) {
        Logger.log("Dragging started on ${item.name}")
    }

    override fun onSwipeStarted(item: Vehicle, viewHolder: ViewHolder) {
        Logger.log("Swiping started on ${item.name}")
    }

    override fun onIsDragging(
        item: Vehicle?,
        viewHolder: ViewHolder,
        offsetX: Int,
        offsetY: Int,
        canvasUnder: Canvas?,
        canvasOver: Canvas?,
        isUserControlled: Boolean) {
        // Call commented out to avoid saturating the log
        //Logger.log("The ${if (isUserControlled) "User" else "System"} is dragging ${item.name} (offset X: $offsetX, offset Y: $offsetY)")
    }

    override fun onIsSwiping(
        item: Vehicle?,
        viewHolder: ViewHolder,
        offsetX: Int,
        offsetY: Int,
        canvasUnder: Canvas?,
        canvasOver: Canvas?,
        isUserControlled: Boolean) {
        // Call commented out to avoid saturating the log
        //Logger.log("The ${if (isUserControlled) "User" else "System"} is swiping ${item?.name} (offset X: $offsetX, offset Y: $offsetY)")
    }

    override fun onDragFinished(item: Vehicle, viewHolder: ViewHolder) {
        Logger.log("Dragging finished on ${item.name} (the item was dropped)")
    }

    override fun onSwipeAnimationFinished(viewHolder: ViewHolder) {
        Logger.log("Swiping animation finished")
    }
}