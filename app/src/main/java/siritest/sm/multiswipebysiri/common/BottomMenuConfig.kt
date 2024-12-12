package siritest.sm.multiswipebysiri.common


enum class ListBottomFragmentType(val index: Int, val tag: String) {
    GRID(2, "GridFragment"),
    VERTICAL(0, "VerticalFragment"),
    HORIZONTAL(1, "HorizontalFragment")

}

data class ListBottomFragmentConfig(
    var isUsingStandardItemLayout: Boolean,
    var isRestrictingDraggingDirections: Boolean,
    var isDrawingBehindSwipedItems: Boolean,
    var isUsingFadeOnSwipedItems: Boolean)

private val listFragmentConfigurations = listOf(

    // Initial state of the vertical-list fragment
    ListBottomFragmentConfig(
        isUsingStandardItemLayout = true,
        isRestrictingDraggingDirections = true,
        isDrawingBehindSwipedItems = true,
        isUsingFadeOnSwipedItems = false),

    // Initial state of the horizontal-list fragment
    ListBottomFragmentConfig(
        isUsingStandardItemLayout = false,
        isRestrictingDraggingDirections = false,
        isDrawingBehindSwipedItems = true,
        isUsingFadeOnSwipedItems = true),

    // Initial state of the grid-list fragment
    ListBottomFragmentConfig(
        isUsingStandardItemLayout = false,
        isRestrictingDraggingDirections = false,
        isDrawingBehindSwipedItems = true,
        isUsingFadeOnSwipedItems = true)
)

var currentBottomListFragmentType = ListBottomFragmentType.VERTICAL
val currentListFragmentConfig
    get() = listFragmentConfigurations[currentBottomListFragmentType.index]