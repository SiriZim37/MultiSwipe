package siritest.sm.multiswipebysiri.fragment

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import siritest.sm.multiswipebysiri.R
import siritest.sm.multiswipebysiri.adapter.SwipeRecyclerView
import siritest.sm.multiswipebysiri.base.BaseListFragment
import siritest.sm.multiswipebysiri.common.currentListFragmentConfig

class VerticalFragment : BaseListFragment() {

    private val numberOfColumns = 2

    override val fragmentLayoutId = R.layout.fragment_grid_list_item
    override val optionsMenuId = R.menu.fragment_grid_list_options

    override fun setupListLayoutManager(list: SwipeRecyclerView) {
        // Set grid linear layout manager
        list.layoutManager = GridLayoutManager(activity, numberOfColumns)
    }

    override fun setupListOrientation(list: SwipeRecyclerView) {
        // It is necessary to set the orientation in code so the list can work correctly.
        // Horizontal swiping is specified because this grid list is vertically scrollable.
        // For horizontally scrollable grid lists, vertical swiping should be used instead.
        list.orientation = SwipeRecyclerView.ListOrientation.GRID_LIST_WITH_HORIZONTAL_SWIPING

        // We set this property to stop the grid list from drawing top dividers in the first row
        list.numOfColumnsPerRowInGridList = numberOfColumns
    }

    override fun setupListItemLayout(list: SwipeRecyclerView) {
        if (currentListFragmentConfig.isUsingStandardItemLayout)
            setStandardItemLayoutAndDivider(list)
        else
            setCardViewItemLayoutAndNoDivider(list)
    }

    private fun setStandardItemLayoutAndDivider(list: SwipeRecyclerView) {
        // In XML: app:item_layout="@layout/list_item_grid_list"
        list.itemLayoutId = R.layout.item_list_grid

        // In XML: app:divider="@drawable/divider_grid_list"
        list.dividerDrawableId = R.drawable.divider_grid_list
    }

    private fun setCardViewItemLayoutAndNoDivider(list: SwipeRecyclerView) {
        // In XML: app:item_layout="@layout/item_list_grid_cardview"
        list.itemLayoutId = R.layout.item_list_grid_cardview

        // In XML: app:divider="@null"
        list.dividerDrawableId = null
    }

    override fun setupLayoutBehindItemLayoutOnSwiping(list: SwipeRecyclerView) {
        // We set to null all the properties that can be used to display something behind swiped items
        // In XML: app:behind_swiped_item_bg_color="@null"
        list.behindSwipedItemBackgroundColor = null

        // In XML: app:behind_swiped_item_bg_color_secondary="@null"
        list.behindSwipedItemBackgroundSecondaryColor = null

        // In XML: app:behind_swiped_item_icon="@null"
        list.behindSwipedItemIconDrawableId = null

        // In XML: app:behind_swiped_item_icon_secondary="@null"
        list.behindSwipedItemIconSecondaryDrawableId = null

        // In XML: app:behind_swiped_item_custom_layout="@null"
        list.behindSwipedItemLayoutId = null

        // In XML: app:behind_swiped_item_custom_layout_secondary="@null"
        list.behindSwipedItemSecondaryLayoutId = null

        val currentContext = context
        if (currentListFragmentConfig.isDrawingBehindSwipedItems && currentContext != null)
            if (currentListFragmentConfig.isUsingStandardItemLayout) {
                // We set certain properties to show an icon and a background colour behind swiped items
                // In XML: app:behind_swiped_item_icon="@drawable/ic_delete_item"
                list.behindSwipedItemIconDrawableId = R.drawable.ic_delete_item

                // In XML: app:behind_swiped_item_icon_secondary="@drawable/ic_archive_item"
                list.behindSwipedItemIconSecondaryDrawableId = R.drawable.ic_archive_item

                // In XML: app:behind_swiped_item_bg_color="@color/swipeBehindBackground"
                list.behindSwipedItemBackgroundColor = ContextCompat.getColor(currentContext, R.color.swipeBehindBackground)

                // In XML: app:behind_swiped_item_bg_color_secondary="@color/swipeBehindBackgroundSecondary"
                list.behindSwipedItemBackgroundSecondaryColor = ContextCompat.getColor(currentContext, R.color.swipeBehindBackgroundSecondary)

                // In XML: app:behind_swiped_item_icon_margin="@dimen/dimen_normal"
                list.behindSwipedItemIconMargin = resources.getDimension(R.dimen.dimen_normal)
            } else {
                // We set our custom layouts to be displayed behind swiped items
                // In XML: app:behind_swiped_item_custom_layout="@layout/behind_swiped_grid_list"
                list.behindSwipedItemLayoutId = R.layout.behind_swiped_grid_list

                // In XML: app:behind_swiped_item_custom_layout_secondary="@layout/behind_swiped_grid_list_secondary"
                list.behindSwipedItemSecondaryLayoutId = R.layout.behind_swiped_grid_list_secondary
            }
    }

    override fun setupFadeItemLayoutOnSwiping(list: SwipeRecyclerView) {
        // In XML: app:swiped_item_opacity_fades_on_swiping="true/false"
        list.reduceItemAlphaOnSwiping = currentListFragmentConfig.isUsingFadeOnSwipedItems
    }

    companion object {
        fun newInstance() = GridFragment()
    }
}