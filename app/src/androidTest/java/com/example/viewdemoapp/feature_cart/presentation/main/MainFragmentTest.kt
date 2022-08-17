package com.example.viewdemoapp.feature_cart.presentation.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.viewdemoapp.R
import com.example.viewdemoapp.instrumented.launchFragmentInHiltContainer
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Reminder: We can replace injected dependencies in 2 ways:
// 1. UninstallModules
// - In individual test you can switch Module::class by using UninstallModules with @BindValue or
// another Module::Class
// - Hilt creates new set of dagger components (a lot of code that needs to be compiled)
// for each test, making it slower to build, but provides
// more flexibility - different tests can have different dependencies
// 2. For ViewModel I dont use Module::class
// Android Developer: Hilt testing best practices

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {
    @BindValue
    @JvmField
    val mainViewModel = MainViewModel(FakeGetCategories(), FakeGetCheckoutOrder())

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainFragment_LaunchedInContainer(): Unit = runBlocking {
        hiltRule.inject()
        launchFragmentInHiltContainer<MainFragment>()
        onView(withId(R.id.itemCategoryName)).check(matches(isDisplayed()))
    }
}
