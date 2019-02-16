package com.ahmed.bakingapp.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.ahmed.bakingapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class OpenIngredientsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openIngredientsTest() {
        // Giving time to the network connection to reply
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Looking for the first Item
        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_recipes_names), withText("Nutella Pie"),
                        childAtPosition(
                                allOf(withId(R.id.card_view_recipes),
                                        childAtPosition(
                                                withId(R.id.list_recipe),
                                                0)),
                                0),
                        isDisplayed()));
        // Check if the first item is the Nutella Pie
        textView.check(matches(withText("Nutella Pie")));

        // Click on the first item of the Recycler - Card view
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list_recipe),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));

        //Click Ingredients
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.tv_recipe_ingredients), withText("Ingredients"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_master_recipe_details_list),
                                        0),
                                0),
                        isDisplayed()));
        // wait 1 second before clicking on Ingredients
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appCompatTextView.perform(click());
        // now the ingredient screen is open
        // wait 1 second before clicking on back to exit the app
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        pressBack();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
