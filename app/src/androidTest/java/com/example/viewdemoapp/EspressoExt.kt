package com.example.viewdemoapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import java.util.concurrent.TimeoutException

fun clickOnViewChild(viewId: Int) = object : ViewAction {
    override fun getConstraints() = null

    override fun getDescription() = "Click on a child view with specified id."

    override fun perform(uiController: UiController, view: View) = ViewActions.click()
        .perform(uiController, view.findViewById<View>(viewId))
}

fun waitUntilGone(timeout: Long) = object : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return Matchers.any(View::class.java)
    }

    override fun getDescription(): String {
        return "wait up to $timeout milliseconds for the view to be gone"
    }

    override fun perform(uiController: UiController, view: View) {

        val endTime = System.currentTimeMillis() + timeout

        do {
            if (view.visibility == View.GONE) return
            uiController.loopMainThreadForAtLeast(50)
        } while (System.currentTimeMillis() < endTime)

        throw PerformException.Builder()
            .withActionDescription(description)
            .withCause(TimeoutException("Waited $timeout milliseconds"))
            .withViewDescription(HumanReadables.describe(view))
            .build()
    }
}

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View?> {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: // has no item on such position
                return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}
