package com.example.viewdemoapp.feature_cart.presentation.end_to_end

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.viewdemoapp.R
import com.example.viewdemoapp.clickOnViewChild
import com.example.viewdemoapp.core.MainActivity
import com.example.viewdemoapp.waitUntilGone
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ProductDetailsEndToEndTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun productDetails_addedAndRemovedFromCart() {
        launchActivity<MainActivity>().use {
            // Wait for data to be loaded
            onView(withId(R.id.loading)).perform(waitUntilGone(30000))

            // Click on first item inside second main recycler view item - go to Product Details
            onView(withId(R.id.mainRecyclerView))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        1,
                        clickOnViewChild(R.id.itemProductElectronics)
                    )
                )

            // Add product to cart
            onView(withId(R.id.add)).perform(click())

            // Check is amount updated
            onView(withId(R.id.currentAmountCard)).check(matches(withText(containsString("1"))))

            // Remove product from cart
            onView(withId(R.id.remove)).perform(click())

            // Check is amount updated
            onView(withId(R.id.currentAmountCard)).check(matches(withText(containsString("0"))))
        }
    }
}
