package com.example.viewdemoapp.feature_cart.presentation.end_to_end

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.viewdemoapp.R
import com.example.viewdemoapp.atPosition
import com.example.viewdemoapp.clickOnViewChild
import com.example.viewdemoapp.core.MainActivity
import com.example.viewdemoapp.waitUntilGone
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ShoppingCartEndToEndTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun shoppingCart_addedAndRemovedFromCart() {
        launchActivity<MainActivity>().use {
            // Wait for data to be loaded
            onView(withId(R.id.loading)).perform(waitUntilGone(30000))

            // Click on first category name  - go to Category Details Screen
            onView(withId(R.id.mainRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

            // Add first product to cart
            onView(withId(R.id.categoryRecyclerView))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        clickOnViewChild(R.id.add)
                    )
                )

            // Go back to main
            onView(withId(R.id.btnBack)).perform(click())

            // Go to shopping cart
            onView(withId(R.id.btnCart)).perform(click())

            // Check is amount updated
            onView(withId(R.id.shoppingCartRecyclerView))
                .check(matches(atPosition(0, hasDescendant(withText("1")))))

            // Remove first product from cart
            onView(withId(R.id.shoppingCartRecyclerView))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        clickOnViewChild(R.id.remove)
                    )
                )

            // Check is amount updated
            onView(withId(R.id.mainRecyclerView)).check(matches(isDisplayed()))
        }
    }
}
