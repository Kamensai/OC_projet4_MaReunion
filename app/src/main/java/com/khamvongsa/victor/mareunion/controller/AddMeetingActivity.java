package com.khamvongsa.victor.mareunion.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.khamvongsa.victor.mareunion.BuildConfig;
import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.di.DI;
import com.khamvongsa.victor.mareunion.model.AddMeetingViewModel;
import com.khamvongsa.victor.mareunion.model.ExampleMeeting;
import com.khamvongsa.victor.mareunion.model.ExampleRoom;
import com.khamvongsa.victor.mareunion.service.CalculTime;
import com.khamvongsa.victor.mareunion.service.CalculTimeService;
import com.khamvongsa.victor.mareunion.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;

public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // UI COMPONENTS
    // DATE
    @BindView(R.id.activity_add_meeting_editDate)
    TextView mEditDate;
    @BindView(R.id.activity_add_meeting_btnAdd_Date)
    FloatingActionButton mBtnDate;

    // HEURE DEBUT
    @BindView(R.id.activity_add_meeting_editStartHour)
    TextView mEditStartHour;
    @BindView(R.id.activity_add_meeting_btnAdd_StartHour)
    FloatingActionButton mBtnStartHour;

    // HEURE FIN
    @BindView(R.id.activity_add_meeting_editEndHour)
    TextView mEditEndHour;
    @BindView(R.id.activity_add_meeting_btnAdd_EndHour)
    FloatingActionButton mBtnEndHour;

    // SALLE
    @BindView(R.id.activity_add_meeting_Room)
    TextView mTextViewRoom;
    @BindView(R.id.activity_add_meeting_spinnerRoom)
    Spinner mSpinnerRoom;

    // SUJET
    @BindView(R.id.activity_add_meeting_inputSubjectMeeting)
    TextInputLayout mInputSubjectReunion;
    @BindView(R.id.activity_add_meeting_editSubjectMeeting)
    EditText mEditSubjectReunion;

    // PARTICIPANT
    @BindView(R.id.activity_add_meeting_editText_participant)
    EditText mEditTextParticipant;
    @BindView(R.id.activity_add_meeting_chipGroup)
    ChipGroup mChipGroupParticipant;
    @BindView(R.id.activity_add_meeting_btnAdd_participant)
    Button mButtonAddParticipant;

    @BindView(R.id.activity_add_meeting_btn_createMeeting)
    MaterialButton addButton;

    private AddMeetingViewModel mAddMeetingViewModel = new AddMeetingViewModel();
    private MeetingApiService mMeetingApiService;
    private CalculTimeService mCalculTimeService;
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;
    private final List<String> mListParticipant = new ArrayList<>();
    private Calendar mStartDate;
    private Calendar mStartHour;
    private Calendar mEndHour;

    private List<ExampleRoom> mAvailableRooms;
    private List<String> listRooms = new ArrayList<>();

    private final static String MEETING_VIEWMODEL = "MEETING_VIEWMODEL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mMeetingApiService = DI.getMeetingApiService();
        mCalculTimeService = new CalculTime();

        // REUNION SUBJECT
        writtenSubject();

        // DATE
        mEditDate.setInputType(InputType.TYPE_NULL);
        startDate();

        //HOUR START
        mEditStartHour.setInputType(InputType.TYPE_NULL);
        startHour();

        // HOUR END
        mEditEndHour.setInputType(InputType.TYPE_NULL);
        endHour();

        // PARTICIPANTS
        writtenParticipant();
        this.mButtonAddParticipant.setOnClickListener(v -> {
            addNewChip(mAddMeetingViewModel.getWrittenParticipant());
            mEditTextParticipant.setText("");
            mAddMeetingViewModel.setParticipants(mListParticipant);
        });
    }

    public void writtenSubject() {
        mEditSubjectReunion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                mAddMeetingViewModel.setSubject(mEditSubjectReunion.getText().toString());
            }
        });
    }

    public void writtenParticipant() {
        mEditTextParticipant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                mAddMeetingViewModel.setWrittenParticipant(mEditTextParticipant.getText().toString());
            }
        });
    }

    public void startDate() {
        mBtnDate.setOnClickListener(v -> {
            mStartDate = null;
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            mDatePicker = new DatePickerDialog(AddMeetingActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        mStartDate = Calendar.getInstance();
                        mStartDate.set(year1,monthOfYear, dayOfMonth);
                        mAddMeetingViewModel.setStartDate(mStartDate);
                        if (mAddMeetingViewModel.isStartDateFilled()) {
                            mEditDate.setError(null);
                        }
                        mEditDate.setText(getResources().getString(R.string.date_string,dayOfMonth, (monthOfYear+1), year1));
                    }, year, month, day);
            mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
            mDatePicker.show();
        });
    }

    public void startHour() {
        mBtnStartHour.setOnClickListener(v -> {
            if (!mAddMeetingViewModel.isStartDateFilled()) {
                mEditDate.setError(getString(R.string.error_startDate_empty));
                return;
            }
            mStartHour = null;
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            // time picker dialog
            mTimePicker = new TimePickerDialog(AddMeetingActivity.this,
                    (tp, sHour, sMinute) -> {
                        mStartHour = Calendar.getInstance();
                        mEditStartHour.setText(getResources().getString(R.string.hour_string,sHour,sMinute));
                        mStartHour.set(0, 0, 0, sHour, sMinute);
                        mAddMeetingViewModel.setStartHour(mStartHour);
                        if (!sameDateTimeinferior(mAddMeetingViewModel.getStartDate(), sHour, sMinute, hour, minutes)){
                            mEditStartHour.setError(null);
                        }
                        if(sameDateTimeinferior(mAddMeetingViewModel.getStartDate(), sHour, sMinute, hour, minutes) && mAddMeetingViewModel.getEndHour() != null) {
                            mEditStartHour.setError(getString(R.string.error_startHour_inferior));
                            availableRoom();
                        }
                        else if (sameDateTimeinferior(mAddMeetingViewModel.getStartDate(), sHour, sMinute, hour, minutes)) {
                            mEditStartHour.setError(getString(R.string.error_startHour_inferior));
                        }
                        else if (mAddMeetingViewModel.getEndHour() != null && !sameDateTimeinferior(mAddMeetingViewModel.getStartDate(), mAddMeetingViewModel.getEndHour().get(Calendar.HOUR), mAddMeetingViewModel.getEndHour().get(Calendar.MINUTE), hour, minutes)){
                            if (sHour < mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY)
                                    || (sHour == mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY) && sMinute < mAddMeetingViewModel.getEndHour().get(Calendar.HOUR))){
                                mEditStartHour.setError(null);
                                availableRoom();
                            }
                            else if (sHour > mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY) && !sameDateTimeinferior(mAddMeetingViewModel.getStartDate(), sHour, sMinute, hour, minutes)
                                    || (sHour == mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY) && sMinute > mAddMeetingViewModel.getEndHour().get(Calendar.MINUTE))
                                    && !sameDateTimeinferior(mAddMeetingViewModel.getStartDate(), sHour, sMinute, hour, minutes)){
                            mEditStartHour.setError(getString(R.string.error_startHour_superior));
                            }
                        }
                    }, hour, minutes, true);
            mTimePicker.show();
        });
    }

    public void endHour() {
        mBtnEndHour.setOnClickListener(v -> {
            if (!mAddMeetingViewModel.isStartHourFilled()) {
                mEditStartHour.setError(getString(R.string.error_startHour_empty));
                return;
            }
            mEndHour = null;
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            // time picker dialog
            mTimePicker = new TimePickerDialog(AddMeetingActivity.this,
                    (tp, sHour, sMinute) -> {
                        mEndHour = Calendar.getInstance();
                        int mStartReunionHourChosen = mAddMeetingViewModel.getStartHour().get(Calendar.HOUR_OF_DAY);
                        int mStartReunionMinuteChosen = mAddMeetingViewModel.getStartHour().get(Calendar.MINUTE);
                        mEditEndHour.setText(getResources().getString(R.string.hour_string,sHour,sMinute));
                        mEndHour.set(0,0,0,sHour,sMinute);
                        mAddMeetingViewModel.setEndHour(mEndHour);
                        if (mStartReunionHourChosen > sHour || (mStartReunionHourChosen == sHour && mStartReunionMinuteChosen > sMinute)) {
                            mEditEndHour.setError(getString(R.string.error_endHour_inferior));
                        }
                        else {
                            if(!sameDateTimeinferior(mAddMeetingViewModel.getStartDate(), mAddMeetingViewModel.getStartHour().get(Calendar.HOUR), mAddMeetingViewModel.getStartHour().get(Calendar.MINUTE), hour, minutes)) {
                                mEditStartHour.setError(null);
                            }
                            if(!sameDateTimeinferior(mAddMeetingViewModel.getStartDate(), mAddMeetingViewModel.getEndHour().get(Calendar.HOUR), mAddMeetingViewModel.getEndHour().get(Calendar.MINUTE), hour, minutes)) {
                                mEditEndHour.setError(null);
                            }
                            else if (mAddMeetingViewModel.getEndHour().get(Calendar.HOUR) > mAddMeetingViewModel.getStartHour().get(Calendar.HOUR)
                                || (mAddMeetingViewModel.getEndHour().get(Calendar.HOUR) == mAddMeetingViewModel.getStartHour().get(Calendar.HOUR)
                                    && mAddMeetingViewModel.getEndHour().get(Calendar.MINUTE) > mAddMeetingViewModel.getStartHour().get(Calendar.MINUTE))){
                                mEditEndHour.setError(null);
                            }

                        }
                        availableRoom();
                    }, hour, minutes, true);
            mTimePicker.show();
        });
    }

    public boolean sameDateTimeinferior(Calendar startDate, int chosenHour, int chosenMinute, int hourOfTheDay, int minutesOfTheDay) {
        return (chosenHour < hourOfTheDay
                && (startDate.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                && (startDate.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH))
                && (startDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))
                || (chosenHour == hourOfTheDay && chosenMinute < minutesOfTheDay)
                && (startDate.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                && (startDate.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH))
                && (startDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)));
    }

    // SPINNER AVAILABLE_ROOM
    public void availableRoom() {
            int mStartReunionMinuteChosen = mAddMeetingViewModel.getStartHour().get(Calendar.MINUTE);
            int mEndReunionMinuteChosen = mAddMeetingViewModel.getEndHour().get(Calendar.MINUTE);
            int mReunionHourTime = mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY) - mAddMeetingViewModel.getStartHour().get(Calendar.HOUR_OF_DAY);
            int mReunionMinuteTime = (mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY) * 60 + mAddMeetingViewModel.getEndHour().get(Calendar.MINUTE)) - (mAddMeetingViewModel.getStartHour().get(Calendar.HOUR_OF_DAY) * 60 + mAddMeetingViewModel.getStartHour().get(Calendar.MINUTE));

            int mReunionMinuteTimeLeft = mCalculTimeService.calculMinuteLeft(mReunionMinuteTime, mReunionHourTime, mStartReunionMinuteChosen, mEndReunionMinuteChosen);
            int mReunionHourTimeLeft = mCalculTimeService.calculHourLeft(mReunionMinuteTime, mReunionHourTime, mStartReunionMinuteChosen, mEndReunionMinuteChosen);

            List<ExampleMeeting> meetings = mMeetingApiService.getMeetings();
            mAvailableRooms = mMeetingApiService.getRooms();
            listRooms = mMeetingApiService.filterAvailableRooms(meetings, mAvailableRooms, mAddMeetingViewModel.getStartDate(), mAddMeetingViewModel.getStartHour(), mAddMeetingViewModel.getEndHour());

            if ((mReunionHourTimeLeft == 0 && mReunionMinuteTimeLeft > 0)
                    || (mReunionHourTimeLeft > 0 && mReunionMinuteTimeLeft > 0)
                    || (mReunionHourTimeLeft > 0 && mReunionMinuteTimeLeft == 0)) {
                Toast.makeText(this, getString(R.string.meeting_time, mReunionHourTimeLeft,mReunionMinuteTimeLeft), LENGTH_SHORT).show();
            }
            if (listRooms.size() > 0) {
            mTextViewRoom.setError(null);
            }
            else {
                mTextViewRoom.setError(getString(R.string.error_room_empty));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listRooms);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerRoom.setAdapter(adapter);
            mSpinnerRoom.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String roomChosen = listRooms.get(position);
        for (int i = 0; i < mAvailableRooms.size(); i++) {
            if(roomChosen.equalsIgnoreCase(mAvailableRooms.get(i).getName())) {
                ExampleRoom mRoom = mAvailableRooms.get(i);
                mAddMeetingViewModel.setRoom(mRoom);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // PARTICIPANT CHIP
    private void addNewChip(String participant) {
        if (participant == null || participant.isEmpty()) {
            Toast.makeText(this, getString(R.string.editText_participant_empty), Toast.LENGTH_LONG).show();
            return;
        }
        if (!isEmailValid(participant)){
            Toast.makeText(this, getString(R.string.editText_participant_notEmail), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            LayoutInflater inflater = LayoutInflater.from(this);

            // Créer un chip à partir du layout
            Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, this.mChipGroupParticipant, false);
            newChip.setText(participant);
            newChip.setTag(participant);
            this.mChipGroupParticipant.addView(newChip);

            newChip.setOnCloseIconClickListener(v -> handleChipCloseIconClicked((Chip) v));
            mListParticipant.add(participant);
        }
        catch (Exception e) {
            e.printStackTrace();
            // Ne s'affiche qu'en mode Debug.
            if(BuildConfig.DEBUG) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // Supprimer un chip
    private void handleChipCloseIconClicked(Chip chip) {
        ChipGroup parent = (ChipGroup) chip.getParent();
        parent.removeView(chip);

        String deletedChip = (String) chip.getTag();
        Iterator<String> i = mAddMeetingViewModel.getListParticipants().iterator();
        while (i.hasNext()) {
            final String participant = i.next();
            if (deletedChip.equalsIgnoreCase(participant)) {
                i.remove();
                break;
            }
        }
    }

    // Vérifie que le texte est bien une adresse mail
    public static boolean isEmailValid(CharSequence email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // ONSAVE AND ONRESTORE
    @Override
    public void onSaveInstanceState(@NonNull Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(MEETING_VIEWMODEL, mAddMeetingViewModel);
    }

    @Override
    public void onRestoreInstanceState(Bundle in) {
        super.onRestoreInstanceState(in);
        mAddMeetingViewModel = in.getParcelable(MEETING_VIEWMODEL);
        mEditSubjectReunion.setText(mAddMeetingViewModel.getSubject());
        if (mAddMeetingViewModel.getStartDate() != null) {
            int dayOfMonth = mAddMeetingViewModel.getStartDate().get(Calendar.DAY_OF_MONTH);
            int month = mAddMeetingViewModel.getStartDate().get(Calendar.MONTH);
            int year = mAddMeetingViewModel.getStartDate().get(Calendar.YEAR);
            mStartDate = Calendar.getInstance();
            mStartDate.set(year,month, dayOfMonth);
            mEditDate.setText(getResources().getString(R.string.date_string,dayOfMonth, (month+1), year));
        }
        if (mAddMeetingViewModel.getStartHour() != null) {
            int startHour = mAddMeetingViewModel.getStartHour().get(Calendar.HOUR_OF_DAY);
            int startMinute = mAddMeetingViewModel.getStartDate().get(Calendar.MINUTE);
            mStartHour = Calendar.getInstance();
            mStartHour.set(0,0,0,startHour,startMinute);
            mEditStartHour.setText(getResources().getString(R.string.hour_string,startHour,startMinute));
        }
        if (mAddMeetingViewModel.getEndHour() != null) {
            int endHour = mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY);
            int endMinute = mAddMeetingViewModel.getEndHour().get(Calendar.MINUTE);
            mEndHour = Calendar.getInstance();
            mEndHour.set(0,0,0,endHour,endMinute);
            mEditEndHour.setText(getResources().getString(R.string.hour_string,endHour,endMinute));
        }

        if(mAddMeetingViewModel.isStartDateFilled() && mAddMeetingViewModel.isStartHourFilled() && mAddMeetingViewModel.isEndHourFilled()) {
            availableRoom();
            mSpinnerRoom.setSelection((int) mAddMeetingViewModel.getRoom().getId());
        }

        List<String> newListParticipant = mAddMeetingViewModel.getListParticipants();
        for (int i = 0; i < newListParticipant.size(); i++) {
            addNewChip(newListParticipant.get(i));
        }
    }

    @OnClick(R.id.activity_add_meeting_btn_createMeeting)
    void addReunion() {
        if (mAddMeetingViewModel.isAllFieldsFilled()) {
            ExampleMeeting reunion = new ExampleMeeting(System.currentTimeMillis(), mAddMeetingViewModel.getStartDate().getTime(), mAddMeetingViewModel.getStartHour().getTime(), mAddMeetingViewModel.getEndHour().getTime(), mAddMeetingViewModel.getRoom(), mAddMeetingViewModel.getSubject(), mAddMeetingViewModel.getListParticipants());
            mMeetingApiService.createMeeting(reunion);
            finish();
        }
        if (!mAddMeetingViewModel.isSubjectFilled()) {
            mEditSubjectReunion.setError(getString(R.string.error_subject_empty));
        }
        if (!mAddMeetingViewModel.isStartDateFilled()) {
            mEditDate.setError(getString(R.string.error_startDate_empty));
        }
        if (!mAddMeetingViewModel.isStartHourFilled()) {
            mEditStartHour.setError(getString(R.string.error_startHour_empty));
        }
        if (!mAddMeetingViewModel.isEndHourFilled()) {
            mEditEndHour.setError(getString(R.string.error_endHour_empty));
        }
        if (!mAddMeetingViewModel.isRoomFilled()) {
            mTextViewRoom.setError(getString(R.string.error_room_empty));
        }
        if (!mAddMeetingViewModel.isListParticipantsFilled()) {
            mEditTextParticipant.setError(getString(R.string.error_lisParticipant_empty));
        }
    }
    //
    public static void navigate (FragmentActivity activity){
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
