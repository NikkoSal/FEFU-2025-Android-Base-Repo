package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kotlin.math.max

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val lineSpacing: Int = context.resources.getDimensionPixelSize(R.dimen.lineSpacing)
    private val itemSpacing: Int = context.resources.getDimensionPixelSize(R.dimen.itemSpacing)
    private val paddingHorizontal: Int = context.resources.getDimensionPixelSize(R.dimen.paddingHorizontal)
    private val paddingVertical: Int = context.resources.getDimensionPixelSize(R.dimen.paddingVertical)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        var totalWidth = 0
        var totalHeight = 0
        var lineWidth = 0
        var lineHeight = 0

        val availableWidth = widthSize - paddingHorizontal * 2
        val childCount = childCount

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE) continue

            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (lineWidth + childWidth + (if (lineWidth > 0) itemSpacing else 0) > availableWidth) {
                totalWidth = max(totalWidth, lineWidth)
                totalHeight += lineHeight + lineSpacing
                lineWidth = childWidth
                lineHeight = childHeight
            } else {
                lineWidth += childWidth + (if (lineWidth > 0) itemSpacing else 0)
                lineHeight = max(lineHeight, childHeight)
            }
        }

        totalWidth = max(totalWidth, lineWidth) + paddingHorizontal * 2
        totalHeight += lineHeight + paddingVertical * 2

        setMeasuredDimension(
            resolveSize(totalWidth, widthMeasureSpec),
            resolveSize(totalHeight, heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val availableWidth = width - paddingHorizontal * 2
        val childCount = childCount

        var currentTop = paddingVertical
        var lineHeight = 0

        var lineViews = mutableListOf<View>()
        var lineWidth = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE) continue

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            val spacing = if (lineViews.isNotEmpty()) itemSpacing else 0

            if (lineWidth + childWidth + spacing > availableWidth) {
                layoutLine(lineViews, lineWidth, currentTop, lineHeight, availableWidth)

                currentTop += lineHeight + lineSpacing
                lineViews.clear()
                lineWidth = 0
                lineHeight = 0
            }

            lineViews.add(child)
            lineWidth += childWidth + spacing
            lineHeight = max(lineHeight, childHeight)
        }

        if (lineViews.isNotEmpty()) {
            layoutLine(lineViews, lineWidth, currentTop, lineHeight, availableWidth)
        }
    }

    private fun layoutLine(
        lineViews: List<View>,
        lineWidth: Int,
        top: Int,
        lineHeight: Int,
        availableWidth: Int
    ) {
        var currentLeft = paddingHorizontal + (availableWidth - lineWidth) / 2

        for ((index, view) in lineViews.withIndex()) {
            val childWidth = view.measuredWidth
            val childHeight = view.measuredHeight

            view.layout(
                currentLeft,
                top,
                currentLeft + childWidth,
                top + childHeight
            )

            currentLeft += childWidth + itemSpacing
        }
    }
}
