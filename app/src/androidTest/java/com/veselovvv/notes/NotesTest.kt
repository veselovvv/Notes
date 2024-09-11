package com.veselovvv.notes

import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.veselovvv.notes.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Check Note List Page is visible
     * Check note list initial state
     * 1. Recreate activity
     * Check Note List Page is visible
     * Check note list initial state
     * 2. Click "Add" button
     * Check Note Fragment is visible
     * Check note initial state
     * 3. Press back button
     * Check Note Fragment is not visible
     * Check Note List Page is visible
     * Check note list initial state
     * 4. Click "Add" button
     * Check Note Fragment is visible
     * Check note initial state
     * 5. Recreate activity
     * Check Note Fragment is visible
     * Check note initial state
     * 6. Click "Save note" button
     * Check Note Fragment is visible
     * Check note initial state
     * 7. Type in title text field text "First note"
     * 8. Click "Save note" button
     * Check Note Fragment is visible
     * Check title state with text "First note"
     * 9. Clear title text field
     * 10. Type in note text field text "Note text"
     * 11. Click "Save note" button
     * Check Note Fragment is not visible
     * Check Note List Page is visible
     * Check note list state with one item with title "No title" and note "Note text"
     */
    @Test
    fun createNoteWithEmptyTitle() {
        val noteListPage = NoteListPage()

        with(noteListPage) {
            checkIsVisible()
            checkNoteListInitialState()

            activityScenarioRule.scenario.recreate()
            checkIsVisible()
            checkNoteListInitialState()

            clickAddButton()
        }

        val notePage = NotePage()

        with(notePage) {
            checkIsVisible()
            checkNoteInitialState()

            pressBack()

            checkIsNotVisible()
        }

        with(noteListPage) {
            checkIsVisible()
            checkNoteListInitialState()

            clickAddButton()
        }

        with(notePage) {
            checkIsVisible()
            checkNoteInitialState()

            activityScenarioRule.scenario.recreate()
            checkIsVisible()
            checkNoteInitialState()

            clickSaveNoteButton()
            checkIsVisible()
            checkNoteInitialState()

            typeInTitleTextField(text = "First note")
            clickSaveNoteButton()
            checkIsVisible()
            checkTitleState(text = "First note")

            clearTitleTextField()
            typeInNoteTextField(text = "Note text")
            clickSaveNoteButton()
            checkIsNotVisible()
        }

        with(noteListPage) {
            checkIsVisible()
            checkNoteListState(
                notes = listOf(
                    Pair("No title", "Note text")
                )
            )
        }
    }
}