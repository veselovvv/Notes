package com.veselovvv.notes

import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.veselovvv.notes.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder
class NotesTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

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

    /**
     * 0. createNoteWithEmptyTitle()
     * Check Note List Page is visible
     * Check note list state with one item with title "No title" and note "Note text"
     * 1. Click "Add" button
     * Check Note Fragment is visible
     * Check note initial state
     * 2. Type in title text field text "Second note"
     * 3. Type in note text field text "Second note text"
     * 4. Click "Save note" button
     * Check Note Fragment is not visible
     * Check Note List Page is visible
     * Check note list state with two items:
     * 1) with title "No title" and note "Note text"
     * 2) with title "Second note" and note "Second note text"
     * 5. Recreate activity
     * Check Note Fragment is not visible
     * Check Note List Page is visible
     * Check note list state with two items:
     * 1) with title "No title" and note "Note text"
     * 2) with title "Second note" and note "Second note text"
     */
    @Test
    fun createNoteWithNotEmptyTitle() {
        createNoteWithEmptyTitle()

        val noteListPage = NoteListPage()

        with(noteListPage) {
            checkIsVisible()
            checkNoteListState(
                notes = listOf(
                    Pair("No title", "Note text")
                )
            )

            clickAddButton()
        }

        val notePage = NotePage()

        with(notePage) {
            checkIsVisible()
            checkNoteInitialState()

            typeInTitleTextField(text = "Second note")
            typeInNoteTextField(text = "Second note text")

            clickSaveNoteButton()
            checkIsNotVisible()
        }

        with(noteListPage) {
            checkIsVisible()
            checkNoteListState(
                notes = listOf(
                    Pair("No title", "Note text"),
                    Pair("Second note", "Second note text")
                )
            )
        }

        activityScenarioRule.scenario.recreate()
        notePage.checkIsNotVisible()

        with(noteListPage) {
            checkIsVisible()
            checkNoteListState(
                notes = listOf(
                    Pair("No title", "Note text"),
                    Pair("Second note", "Second note text")
                )
            )
        }
    }

    /**
     * 0. createNoteWithEmptyTitle()
     * Check Note List Page is visible
     * Check note list state with one item with title "No title" and note "Note text"
     * 1. Click on first item in list (index = 0)
     * Check Note Fragment is visible
     * Check title state with text "No title"
     * Check note state with text "Note text"
     * 2. Type in title text field text "First note"
     * 3. Type in note text field text "Note text edited"
     * 4. Click "Save note" button
     * Check Note Fragment is not visible
     * Check Note List Page is visible
     * Check note list state with one item with title "First note" and note "Note text edited"
     * 5. Recreate activity
     * Check Note Fragment is not visible
     * Check Note List Page is visible
     * Check note list state with one item with title "First note" and note "Note text edited"
     */
    @Test
    fun editNote() {
        createNoteWithEmptyTitle()

        val noteListPage = NoteListPage()

        with(noteListPage) {
            checkIsVisible()
            checkNoteListState(
                notes = listOf(
                    Pair("No title", "Note text")
                )
            )

            clickOnItemInList(index = 0)
        }

        val notePage = NotePage()

        with(notePage) {
            checkIsVisible()

            checkTitleState(text = "No title")
            checkNoteState(text = "Note text")

            typeInTitleTextField(text = "First note")
            typeInNoteTextField(text = "Note text edited")

            clickSaveNoteButton()
            checkIsNotVisible()
        }

        with(noteListPage) {
            checkIsVisible()
            checkNoteListState(
                notes = listOf(
                    Pair("First note", "Note text edited")
                )
            )
        }

        activityScenarioRule.scenario.recreate()
        notePage.checkIsNotVisible()

        with(noteListPage) {
            checkIsVisible()
            checkNoteListState(
                notes = listOf(
                    Pair("First note", "Note text edited")
                )
            )
        }
    }

    /**
     * 0. createNoteWithEmptyTitle()
     * Check Note List Page is visible
     * Check note list state with one item with title "No title" and note "Note text"
     * 1. Click "Delete" button on first element in list (index = 0)
     * Check Note List Page is visible
     * Check note list initial state
     * 2. Recreate activity
     * Check Note List Page is visible
     * Check note list initial state
     */
    @Test
    fun deleteNote() {
        createNoteWithEmptyTitle()

        val noteListPage = NoteListPage()

        with(noteListPage) {
            checkIsVisible()
            checkNoteListState(
                notes = listOf(
                    Pair("No title", "Note text")
                )
            )

            clickDeleteButton(index = 0)
        }

        with(noteListPage) {
            checkIsVisible()
            checkNoteListInitialState()
        }

        activityScenarioRule.scenario.recreate()

        with(noteListPage) {
            checkIsVisible()
            checkNoteListInitialState()
        }
    }
}