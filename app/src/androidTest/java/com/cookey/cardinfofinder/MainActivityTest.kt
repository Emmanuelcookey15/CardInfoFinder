package com.cookey.cardinfofinder

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.cookey.cardinfofinder.utils.isConnectedToTheInternet
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest : TestCase() {

    @get:Rule
    var permissionRule
            = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_WIFI_STATE,
        android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE)

    private lateinit var instrumentationContext: Context

    private lateinit var activityScenario : ActivityScenario<MainActivity>



    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun test_launch() {
        Espresso.onView(withId(R.id.frame_layout))
    }

    @Test
    fun test_that_information_header_display_on_first_page_is_accurate(){
        Espresso.onView(withId(R.id.header_information))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.intro_details)))
    }

    @Test
    fun test_to_check_card_number_input_field_is_displayed(){
        Espresso.onView(withId(R.id.edt_card_number))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_to_progress_bar_dialog_input_field_is_not_displayed_on_launch(){
        Espresso.onView(withId(R.id.progress_bar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }



    @Test
    fun test_scan_card_button_is_visible(){
        Espresso.onView(withId(R.id.scan_btn))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }


    @Test
    fun test_scan_card_button_is_clickable(){
        Espresso.onView(withId(R.id.scan_btn))
            .perform(ViewActions.click())
    }

}