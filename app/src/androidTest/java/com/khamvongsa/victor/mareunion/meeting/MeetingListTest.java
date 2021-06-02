package com.khamvongsa.victor.mareunion.meeting;

import android.widget.DatePicker;

import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.controller.AddMeetingActivity;
import com.khamvongsa.victor.mareunion.controller.MainActivity;
import com.khamvongsa.victor.mareunion.utils.DeleteMeetingViewAction;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.khamvongsa.victor.mareunion.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    private static final int ITEMS_COUNT = 5;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    /**
     * We ensure that our recyclerview is displaying at least one item
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0)))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we click on AddBtn, AddMeetingActivity is launched.
     */
    @Test
    public void clickOnAddMeetingBtnToAddMeetingActivity() {
        Intents.init();
        onView(withId(R.id.add_Reunion)).perform(click());
        intended(hasComponent(AddMeetingActivity.class.getName()));
    }

    @Test
    public void showOnlyChosenRoomMario() {

        // Vérifie le nombre de réunion
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT));
        // Clique sur le menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        // Clique sur Mario
        onView(withText("Mario")).perform(click());
        // Une seule réunion qui est prévue à la salle Mario
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT - 4));
    }

    @Test
    public void showOnlyChosenRoomLuigi() {
        // Vérifie le nombre de réunion
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT));
        // Clique sur le menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        // Clique sur Luigi
        onView(withText("Luigi")).perform(click());
        // Une seule réunion qui est prévue à la salle Luigi
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT - 4));
    }

    @Test
    public void showOnlyChosenRoomPeach() {
        // Vérifie le nombre de réunion
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT));
        // Clique sur le menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        // Clique sur Peach
        onView(withText("Peach")).perform(click());
        // Une seule réunion qui est prévue à la salle Peach
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT - 2));
    }

    @Test
    public void showOnlyChosenDate() {
        // Vérifie le nombre de réunion
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT));
        // Clique sur le menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        // Clique sur Date pour lancer le DatePicker
        onView(withText("Date")).perform(click());
        //Choisi la date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,9,5));
        // Clique sur ok pour confirmer et fermier le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // Une seule réunion qui est prévue à la date choisie le 05/09/22
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT - 4));
    }

    /**
     * When we delete an item, the item is no more shown
     */

    @Test
    public void meetingList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteMeetingViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(ITEMS_COUNT - 1));
    }
}
