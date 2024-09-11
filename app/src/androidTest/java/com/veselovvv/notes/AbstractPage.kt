package com.veselovvv.notes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

abstract class AbstractPage(protected val rootLayout: Int) {
    protected open val interaction: ViewInteraction = onView(withId(rootLayout))

    fun checkIsVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun checkIsNotVisible() {
        interaction.check(doesNotExist())
    }
}