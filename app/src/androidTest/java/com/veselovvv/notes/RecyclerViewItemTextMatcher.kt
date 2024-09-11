package com.veselovvv.notes

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withRecyclerViewItemText(@IdRes viewId: Int, text: String): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("RecyclerView item with text: $text")
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val adapter = recyclerView.adapter

            for (i in 0 until adapter!!.itemCount) {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(i)
                val textView = viewHolder?.itemView?.findViewById<TextView>(viewId)

                if (textView != null && textView.text.toString() == text) return true
            }

            return false
        }
    }
}
