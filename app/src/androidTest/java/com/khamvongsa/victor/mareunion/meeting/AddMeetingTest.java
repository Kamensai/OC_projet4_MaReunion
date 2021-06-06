
package com.khamvongsa.victor.mareunion.meeting;

import android.widget.DatePicker;
import android.widget.TimePicker;

import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.controller.MainActivity;
import com.khamvongsa.victor.mareunion.utils.DeleteChipViewAction;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.khamvongsa.victor.mareunion.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class AddMeetingTest {

    private static final String SUBJECT = "Subject";
    private static final String PARTICIPANT = "participant@hotmail.fr";
    private static final int PARTICIPANT_COUNT = 0;
    private static final int MEETING_ITEMS_COUNT = 5;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void writeTextSubjectMeeting() {
        // On va sur 'AddMeetingActivity'
        onView(withId(R.id.add_Meeting)).perform(click());
        // On écrit dans l'activity_add_meeting_editSubjectMeeting
        onView(withId(R.id.activity_add_meeting_editSubjectMeeting)).perform(typeText(SUBJECT));
        // Check if the value is true
        onView(withId(R.id.activity_add_meeting_editSubjectMeeting)).check(matches(withText(SUBJECT)));
    }

    @Test
    public void writeTextParticipantToAdd() {
        // On va sur 'AddMeetingActivity'
        onView(withId(R.id.add_Meeting)).perform(click());
        // On écrit dans l'activity_add_meeting_editText_participant
        onView(withId(R.id.activity_add_meeting_editText_participant)).perform(typeText(PARTICIPANT));
        // Check if the value is true
        onView(withId(R.id.activity_add_meeting_editText_participant)).check(matches(withText(PARTICIPANT)));
    }

    /**
     * Lors du click sur le bouton "add_Meeting", envoie un message d'erreur à l'endroit où le champ est vide.
     */
    @Test
    public void errorMessagesShown(){
        // On va sur 'AddMeetingActivity'
        onView(withId(R.id.add_Meeting)).perform(click());
        // Cliquer pour créer une réunion alors que tous les champs sont vides
        onView(withId(R.id.activity_add_meeting_btn_createMeeting)).perform(click());
        // Vérifie si le message d'erreur s'affiche correctement dans 'activity_add_meeting_editSubjectMeeting'
        onView(withId(R.id.activity_add_meeting_editSubjectMeeting)).check(matches(hasErrorText("Veuillez choisir un sujet de réunion.")));

        // On écrit dans 'activity_add_meeting_editSubjectMeeting'
        onView(withId(R.id.activity_add_meeting_editSubjectMeeting)).perform(typeText(SUBJECT), closeSoftKeyboard());
        // Cliquer pour créer une réunion alors que certains champs sont vides
        onView(withId(R.id.activity_add_meeting_btn_createMeeting)).perform(click());
        // Vérifie si le message d'erreur s'affiche correctement dans 'activity_add_meeting_editDate'
        onView(withId(R.id.activity_add_meeting_editDate)).check(matches(hasErrorText("Veuillez choisir une date.")));


        // Clique sur DatePickerButton
        onView(withId(R.id.activity_add_meeting_btnAdd_Date)).perform(click());
        // Set Date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,8,10));
        // Clique sur ok pour confirmer et fermer le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // Cliquer pour créer une réunion alors que certains champs sont vides
        onView(withId(R.id.activity_add_meeting_btn_createMeeting)).perform(click());
        // Vérifie si le message d'erreur s'affiche correctement dans 'activity_add_meeting_editStartHour'
        onView(withId(R.id.activity_add_meeting_editStartHour)).check(matches(hasErrorText("Veuillez choisir une heure de début.")));

        // Clique sur StartTimePickerButton
        onView(withId(R.id.activity_add_meeting_btnAdd_StartHour)).perform(click());
        //Set StartTime
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(14,30));
        // Clique sur ok pour confirmer et fermer le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // Cliquer pour créer une réunion alors que certains champs sont vides
        onView(withId(R.id.activity_add_meeting_btn_createMeeting)).perform(click());
        // Vérifie si le message d'erreur s'affiche correctement dans 'activity_add_meeting_editStartHour'
        onView(withId(R.id.activity_add_meeting_editEndHour)).check(matches(hasErrorText("Veuillez choisir une heure de fin.")));

        // Clique sur EndTimePickerButton
        onView(withId(R.id.activity_add_meeting_btnAdd_EndHour)).perform(click());
        //Set EndTime
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(15,40));
        // Clique sur ok pour confirmer et fermer le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // Cliquer pour créer une réunion alors que certains champs sont vides
        onView(withId(R.id.activity_add_meeting_btn_createMeeting)).perform(click());
        // Vérifie si le message d'erreur s'affiche correctement dans 'activity_add_meeting_editStartHour
        onView(withId(R.id.activity_add_meeting_editText_participant)).check(matches(hasErrorText("Veuillez ajouter un participant.")));
    }

    /**
     * Vérifier si le spinner est lancé, une fois les champs remplis
     */
    @Test
    public void showSpinnerSelectionAvailable() {
        final String dateChosen = "10/8/2022";
        final String startTimeChosen = "14:30";
        final String endTimeChosen = "15:40";
        final String roomMario = "Mario";

        // On va sur 'AddMeetingActivity'
        onView(withId(R.id.add_Meeting)).perform(click());

        // Clique sur DatePickerButton
        onView(withId(R.id.activity_add_meeting_btnAdd_Date)).perform(click());
        // Set Date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,8,10));
        // Clique sur ok pour confirmer et fermier le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // vérifie que la date est bien notée
        onView(withId(R.id.activity_add_meeting_editDate)).check(matches(withText(dateChosen)));


        // Clique sur StartTimePickerButton
        onView(withId(R.id.activity_add_meeting_btnAdd_StartHour)).perform(click());
        //Set StartTime
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(14,30));
        // Clique sur ok pour confirmer et fermer le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // vérifie que le startTime est bien notée
        onView(withId(R.id.activity_add_meeting_editStartHour)).check(matches(withText(startTimeChosen)));

        // Clique sur EndTimePickerButton
        onView(withId(R.id.activity_add_meeting_btnAdd_EndHour)).perform(click());
        //Set EndTime
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(15,40));
        // Clique sur ok pour confirmer et fermer le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // vérifie que le endTime est bien notée
        onView(withId(R.id.activity_add_meeting_editEndHour)).check(matches(withText(endTimeChosen)));

        //Vérifie si le spinner montre bien le String Item "Mario"
        onView(withId(R.id.activity_add_meeting_spinnerRoom)).check(matches(withSpinnerText(roomMario)));
    }

    /**
     * Vérifie l'ajout d'un mail
     */
    @Test
    public void showAddParticipantWithSuccess(){
        // On va sur 'AddMeetingActivity'
        onView(withId(R.id.add_Meeting)).perform(click());

        // On écrit dans l'EditText et on ferme le clavier
        onView(withId(R.id.activity_add_meeting_editText_participant)).perform(typeText(PARTICIPANT), closeSoftKeyboard());
        // Cliquer sur le bouton ajouter un participant
        onView(withId(R.id.activity_add_meeting_btnAdd_participant)).perform(click());
        // Vérifier si le participant s'affiche dans la liste
        onView(withId(R.id.activity_add_meeting_chipGroup)).check(matches(hasChildCount(PARTICIPANT_COUNT+1)));
        onView(withId(R.id.activity_add_meeting_chipGroup)).check(matches(withChild(withText(PARTICIPANT))));
    }

    /**
     * Vérifie la suppression d'un mail
     */
    @Test
    public void showDeleteParticipantWithSuccess(){
        // On va sur 'AddMeetingActivity'
        onView(withId(R.id.add_Meeting)).perform(click());
        // On écrit dans l'EditText et on ferme le clavier
        onView(withId(R.id.activity_add_meeting_editText_participant)).perform(typeText(PARTICIPANT), closeSoftKeyboard());
        // Cliquer sur le bouton ajouter un participant
        onView(withId(R.id.activity_add_meeting_btnAdd_participant)).perform(click());
        // Vérifier si le participant (chip) s'affiche dans la liste
        onView(withId(R.id.activity_add_meeting_chipGroup)).check(matches(hasChildCount(PARTICIPANT_COUNT +1)));
        onView(withId(R.id.activity_add_meeting_chipGroup)).check(matches(withChild(withText(PARTICIPANT))));
        // Supprimer le participant (chip)
        onView(withId(R.id.chip)).perform(new DeleteChipViewAction());
        // Vérifier que le participant (chip) ne s'affiche dans la liste
        onView(withId(R.id.activity_add_meeting_chipGroup)).check(matches(hasChildCount(PARTICIPANT_COUNT)));
    }

    /**
     * Créé une réunion.
     */
    @Test
    public void clickOnCreateMeetingBtn_ToMeetingLit(){
        final String dateChosen = "10/8/2022";
        final String startTimeChosen = "14:30";
        final String endTimeChosen = "15:40";

        // On vérifie le nombre de réunions (5)
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(MEETING_ITEMS_COUNT));

        // On va sur AddMeetingActivity
        onView(withId(R.id.add_Meeting)).perform(click());

        // On écrit dans l'EditText
        onView(withId(R.id.activity_add_meeting_editSubjectMeeting)).perform(typeText(SUBJECT));

        // Cllique sur DatePickerButton
        onView(withId(R.id.activity_add_meeting_btnAdd_Date)).perform(click());
        // Set Date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,8,10));
        // Clique sur ok pour confirmer et fermier le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // vérifie que la date est bien notée
        onView(withId(R.id.activity_add_meeting_editDate)).check(matches(withText(dateChosen)));

        // Click on StartTimePickerButton
        onView(withId(R.id.activity_add_meeting_btnAdd_StartHour)).perform(click());
        //Set StartTime
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(14,30));
        // Clique sur ok pour confirmer et fermier le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // Vérifie que le startTime est bien noté
        onView(withId(R.id.activity_add_meeting_editStartHour)).check(matches(withText(startTimeChosen)));

        // Cliquer sur EndTimePickerButton
        onView(withId(R.id.activity_add_meeting_btnAdd_EndHour)).perform(click());
        //Set EndTime
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(15,40));
        // Clique sur ok pour confirmer et fermer le dialog
        onView(anyOf(withText(android.R.string.ok), withId(android.R.id.button1))).inRoot(isDialog()).perform(click());
        // vérifie que le endTime est bien noté
        onView(withId(R.id.activity_add_meeting_editEndHour)).check(matches(withText(endTimeChosen)));

        // On choisit la salle "Luigi" dans le Spinner
        onView(withId(R.id.activity_add_meeting_spinnerRoom)).perform(click());
        onData(allOf((instanceOf(String.class)))).atPosition(1).perform(click());

        // On écrit dans 'activity_add_meeting_editText_participant' et on ferme le clavier
        onView(withId(R.id.activity_add_meeting_editText_participant)).perform(typeText(PARTICIPANT), closeSoftKeyboard());
        // Cliquer sur le bouton ajouter un participant
        onView(withId(R.id.activity_add_meeting_btnAdd_participant)).perform(click());
        // Vérifier si le participant s'affiche dans la liste
        onView(withId(R.id.activity_add_meeting_chipGroup)).check(matches(hasChildCount(PARTICIPANT_COUNT+1)));

        // Cliquer sur le bouton créer une réunion
        onView(withId(R.id.activity_add_meeting_btn_createMeeting)).perform(click());
        // On vérifie qu'il y a une réunion en plus (5+1 = 6) dans MainActivity
        onView(allOf(withId(R.id.list_meeting), withParentIndex(0))).check(withItemCount(MEETING_ITEMS_COUNT + 1));
    }
}
