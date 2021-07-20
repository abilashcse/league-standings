package com.abilashcse.leaguestandings


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {

        val leagueStandingsTextView = onView(
            allOf(
                withText("League Standings"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        leagueStandingsTextView.check(matches(withText("League Standings")))
        Thread.sleep(5000)
        val matchPlayedTextView = onView(
            allOf(
                withId(R.id.match_played), withText("P"),
                withParent(
                    allOf(
                        withId(R.id.header),
                        withParent(withId(R.id.linearLayout))
                    )
                ),
                isDisplayed()
            )
        )
        matchPlayedTextView.check(matches(withText("P")))
        matchPlayedTextView.check(matches(hasTextColor(R.color.purple_700)))

        val matchWonTextView = onView(
            allOf(
                withId(R.id.match_won), withText("W"),
                withParent(
                    allOf(
                        withId(R.id.header),
                        withParent(withId(R.id.linearLayout))
                    )
                ),
                isDisplayed()
            )
        )
        matchWonTextView.check(matches(withText("W")))
        matchWonTextView.check(matches(hasTextColor(R.color.green)))
        val matchDrawnTextView = onView(
            allOf(
                withId(R.id.match_draw), withText("D"),
                withParent(
                    allOf(
                        withId(R.id.header),
                        withParent(withId(R.id.linearLayout))
                    )
                ),
                isDisplayed()
            )
        )
        matchDrawnTextView.check(matches(withText("D")))

        val matchLostTextView = onView(
            allOf(
                withId(R.id.match_lost), withText("L"),
                withParent(
                    allOf(
                        withId(R.id.header),
                        withParent(withId(R.id.linearLayout))
                    )
                ),
                isDisplayed()
            )
        )
        matchLostTextView.check(matches(withText("L")))
        matchLostTextView.check(matches(hasTextColor(R.color.red)))

        val pointsTextView = onView(
            allOf(
                withId(R.id.points), withText("Pts"),
                withParent(
                    allOf(
                        withId(R.id.header),
                        withParent(withId(R.id.linearLayout))
                    )
                ),
                isDisplayed()
            )
        )
        pointsTextView.check(matches(withText("Pts")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
