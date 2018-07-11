package com.common.githubviewer

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.common.githubviewer.view.activity.MainActivity
import com.common.githubviewer.view.adapter.RepoAdapter
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class MainInstrumentedTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.common.githubviewer", appContext.packageName)
    }


    @Test
    fun testRecyclerViewItemClickLaunchesBrowserActivity() {


        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RepoAdapter.ViewHolder>(2))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RepoAdapter.ViewHolder>(2, click()))

        assert(onView(ViewMatchers.withId(R.id.recyclerView)) == null)
    }
}
