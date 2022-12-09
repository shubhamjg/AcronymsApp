package com.shubham.acronymsapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shubham.acronymsapp.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AcronymUITest {

    @get:Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun acronymSearch(){
        onView(withId(R.id.search_acronym)).perform(typeText("HTTP"))
    }

    @Test
    fun deleteSearchText(){
        onView(withId(R.id.search_acronym)).perform(clearText())
    }
}