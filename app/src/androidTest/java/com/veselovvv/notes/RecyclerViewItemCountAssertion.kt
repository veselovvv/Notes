package com.veselovvv.notes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object RecyclerViewItemCountAssertion {
    fun withItemCount(expectedCount: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("RecyclerView should have $expectedCount items")
            }

            override fun matchesSafely(view: View): Boolean {
                return (view as RecyclerView).adapter?.itemCount == expectedCount
            }
        }
    }
}