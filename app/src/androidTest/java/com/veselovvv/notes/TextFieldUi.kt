package com.veselovvv.notes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.CoreMatchers.allOf

class TextFieldUi {
    fun interaction(textFieldId: Int) = onView(
        allOf(
            withId(textFieldId),
            isAssignableFrom(TextInputEditText::class.java)
        )
    )

    fun checkIsDisplayed(textFieldId: Int) {
        interaction(textFieldId).check(matches(isDisplayed()))
    }

    fun checkTextMatches(textFieldId: Int, text: String) {
        interaction(textFieldId).check(matches(withText(text)))
    }

    fun typeIn(textFieldId: Int, text: String) {
        interaction(textFieldId).perform(clearText()).perform(typeText(text))
    }
}
