package com.veselovvv.notes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId

class ButtonUi {
    fun click(buttonId: Int) {
        onView(withId(buttonId)).perform(click())
    }
}
