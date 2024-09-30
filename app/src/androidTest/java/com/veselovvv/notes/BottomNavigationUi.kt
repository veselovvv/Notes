package com.veselovvv.notes

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId

class BottomNavigationUi {
    fun clickOnTab(@IdRes tabId: Int) {
        onView(withId(tabId)).perform(click())
    }

    fun clickOnFavoritesTab() = clickOnTab(R.id.favorites)
    fun clickOnHomeTab() = clickOnTab(R.id.home)
}