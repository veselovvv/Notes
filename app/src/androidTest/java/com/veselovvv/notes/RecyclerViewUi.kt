package com.veselovvv.notes

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.veselovvv.notes.RecyclerViewItemCountAssertion.withItemCount
import org.hamcrest.Matchers.allOf
import java.text.SimpleDateFormat
import java.util.Date

class RecyclerViewUi(recyclerViewId: Int) {
    private val interaction: ViewInteraction = onView(
        allOf(
            withId(recyclerViewId),
            isAssignableFrom(RecyclerView::class.java)
        )
    )

    fun clickOnItemInList(index: Int) {
        interaction.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(index, click()))
    }

    fun checkNoteListInitialState() {
        interaction.check(matches(isDisplayed()))
            .check(matches(withItemCount(0)))
    }

    fun checkNoteListState(notes: List<Pair<String, String>>) {
        notes.forEachIndexed { index, (title, note) ->
            val date = SimpleDateFormat.getDateInstance().format(Date())

            interaction.perform(scrollToPosition<RecyclerView.ViewHolder>(index))
                .check(matches(isDisplayed()))
                .check(matches(withRecyclerViewItemText(R.id.note_title, title)))
                .check(matches(withRecyclerViewItemText(R.id.note_text, note)))
                .check(matches(withRecyclerViewItemText(R.id.note_date, date)))
        }
    }

    fun clickDeleteButton(index: Int) {
        interaction.perform(scrollToPosition<RecyclerView.ViewHolder>(index))
            .perform(clickOnViewChild(R.id.delete_note))
    }

    fun clickOnMakeFavoriteNoteButton(index: Int) {
        interaction.perform(scrollToPosition<RecyclerView.ViewHolder>(index))
            .perform(clickOnViewChild(R.id.make_favorite_note))
    }
}
