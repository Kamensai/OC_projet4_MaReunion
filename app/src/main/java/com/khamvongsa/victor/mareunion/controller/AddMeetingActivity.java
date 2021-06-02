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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.di.DI;
import com.khamvongsa.victor.mareunion.model.AddMeetingViewModel;
import com.khamvongsa.victor.mareunion.service.CalculTime;
import com.khamvongsa.victor.mareunion.service.CalculTimeService;
import com.khamvongsa.victor.mareunion.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;

public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // UI COMPONENTS
    // DATE Reunion
    @BindView(R.id.activity_add_meeting_inputDate)
    TextInputLayout mDateInput;
    @BindView(R.id.activity_add_meeting_editDate)
    EditText mEditDate;
    @BindView(R.id.activity_add_meeting_btnAdd_Date)
    FloatingActionButton mBtnDate;

    // Début Reunion
    @BindView(R.id.activity_add_meeting_inputStartHour)
    TextInputLayout mStartHourInput;
    @BindView(R.id.activity_add_meeting_editStartHour)
    EditText mEditStartHour;
    @BindView(R.id.activity_add_meeting_btnAdd_StartHour)
    FloatingActionButton mBtnStartHour;

    // Fin de Reunion
    @BindView(R.id.activity_add_meeting_inputEndHour)
    TextInputLayout mEndHourInput;
    @BindView(R.id.activity_add_meeting_editEndHour)
    EditText mEditEndHour;
    @BindView(R.id.activity_add_meeting_btnAdd_EndHour)
    FloatingActionButton mBtnEndHour;

    @BindView(R.id.activity_add_meeting_Room)
    TextView mTextViewRoom;
    @BindView(R.id.activity_add_meeting_spinnerRoom)
    Spinner mSpinnerRoom;

    @BindView(R.id.activity_add_meeting_inputSubjectMeeting)
    TextInputLayout mInputSubjectReunion;
    @BindView(R.id.activity_add_meeting_editSubjectMeeting)
    EditText mEditSubjectReunion;

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
    private List<String> listRooms = new ArrayList<String>();

    private final static String MEETING_VIEWMODEL = "MEETING_VIEWMODEL";

    private final static String TAG = AddMeetingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMeetingApiService = DI.getReunionApiService();
        mCalculTimeService = new CalculTime();

        // REUNION SUBJECT
        subjectChosen();

        // DATE
        mEditDate.setInputType(InputType.TYPE_NULL);
        dateStart();

        //HOUR START
        mEditStartHour.setInputType(InputType.TYPE_NULL);
        hourStart();

        // HOUR END
        mEditEndHour.setInputType(InputType.TYPE_NULL);
        endHour();

        //SPINNER

        // PARTICIPANTS
        writtenParticipant();
        this.mButtonAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewChip(mEditTextParticipant.getText().toString());
                mEditTextParticipant.setText("");
                mAddMeetingViewModel.setParticipants(mListParticipant);
            }
        });
    }

    public void subjectChosen() {
        mEditSubjectReunion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAddMeetingViewModel.setSubject(mEditSubjectReunion.getText().toString());
            }
        });
    }

    public void writtenParticipant() {
        mEditTextParticipant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAddMeetingViewModel.setWrittenParticipant(mEditTextParticipant.getText().toString());
            }
        });
    }

    public void dateStart() {
        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartDate = null;
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                mDatePicker = new DatePickerDialog(AddMeetingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mStartDate = Calendar.getInstance();
                                mStartDate.set(year,monthOfYear, dayOfMonth);
                                mAddMeetingViewModel.setStartDate(mStartDate);
                                if (mAddMeetingViewModel.isStartDateFilled()) {
                                    mEditDate.setError(null);
                                }
                                mEditDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });
    }

    public void hourStart() {
        mBtnStartHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAddMeetingViewModel.isStartDateFilled()) {
                    mEditDate.setError("Veuillez choisir une date de départ.");
                    return;
                }
                mStartHour = null;
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                mTimePicker = new TimePickerDialog(AddMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                mStartHour = Calendar.getInstance();
                                mEditStartHour.setText(sHour + ":" + sMinute);
                                mStartHour.set(0, 0, 0, sHour, sMinute);
                                mAddMeetingViewModel.setStartHour(mStartHour);
                                if (sHour < hour
                                        && (mStartDate.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                                        && (mStartDate.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH))
                                        && (mStartDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))
                                    || (sHour == hour && sMinute < minutes)
                                        && (mStartDate.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                                        && (mStartDate.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH))
                                        && (mStartDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)))  {
                                    mEditStartHour.setError("Veuillez choisir une heure de début inférieure.");
                                }
                                else if (mAddMeetingViewModel.getEndHour() != null){
                                if (sHour > mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY)
                                    || (sHour == mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY) && sMinute > mAddMeetingViewModel.getEndHour().get(Calendar.HOUR))){
                                    mEditStartHour.setError("Veuillez choisir une heure de début inférieure.");
                                    availableRoom();
                                }}
                                else {
                                    mEditStartHour.setError(null);
                                }
                            }
                        }, hour, minutes, true);
                mTimePicker.show();
            }
        });
    }

    public void endHour() {
        mBtnEndHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAddMeetingViewModel.isStartHourFilled()) {
                    mEditStartHour.setError("Veuillez choisir une heure de fin.");
                    return;
                }
                mEndHour = null;
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                mTimePicker = new TimePickerDialog(AddMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                mEndHour = Calendar.getInstance();
                                int mStartReunionHourChosen = mAddMeetingViewModel.getStartHour().get(Calendar.HOUR_OF_DAY);
                                int mStartReunionMinuteChosen = mAddMeetingViewModel.getStartHour().get(Calendar.MINUTE);
                                mEditEndHour.setText(sHour + ":" + sMinute);
                                mEndHour.set(0,0,0,sHour,sMinute);
                                mAddMeetingViewModel.setEndHour(mEndHour);
                                if (mStartReunionHourChosen > sHour || (mStartReunionHourChosen == sHour && mStartReunionMinuteChosen > sMinute)) {
                                    mEditEndHour.setError("Veuillez choisir une heure de fin supérieure.");
                                    availableRoom();
                                }
                                else {
                                    mEditStartHour.setError(null);
                                    mEditEndHour.setError(null);
                                }
                                availableRoom();
                            }
                        }, hour, minutes, true);
                mTimePicker.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(MEETING_VIEWMODEL, mAddMeetingViewModel);
    }

    @Override
    public void onRestoreInstanceState(Bundle in) {
        super.onRestoreInstanceState(in);
        mAddMeetingViewModel = in.getParcelable(MEETING_VIEWMODEL);
        // Tout resetter !!

        mEditSubjectReunion.setText(mAddMeetingViewModel.getSubject());
        if (mAddMeetingViewModel.getStartDate() != null) {
            mStartDate = Calendar.getInstance();
            mStartDate.set(mAddMeetingViewModel.getStartDate().get(Calendar.YEAR),mAddMeetingViewModel.getStartDate().get(Calendar.MONTH), mAddMeetingViewModel.getStartDate().get(Calendar.DAY_OF_MONTH));
            mEditDate.setText(mAddMeetingViewModel.getStartDate().get(Calendar.DAY_OF_MONTH) + "/" + (mAddMeetingViewModel.getStartDate().get(Calendar.MONTH) + 1) + "/" + mAddMeetingViewModel.getStartDate().get(Calendar.YEAR));
        }
        if (mAddMeetingViewModel.getStartHour() != null) {
            mStartHour = Calendar.getInstance();
            mStartHour.set(0,0,0,mAddMeetingViewModel.getStartHour().get(Calendar.HOUR_OF_DAY),mAddMeetingViewModel.getStartHour().get(Calendar.MINUTE));
            mEditStartHour.setText(mAddMeetingViewModel.getStartHour().get(Calendar.HOUR_OF_DAY)+":"+mAddMeetingViewModel.getStartHour().get(Calendar.MINUTE));

        }
        if (mAddMeetingViewModel.getEndHour() != null) {
            mEndHour = Calendar.getInstance();
            mEndHour.set(0,0,0,mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY),mAddMeetingViewModel.getEndHour().get(Calendar.MINUTE));
            mEditEndHour.setText(mAddMeetingViewModel.getEndHour().get(Calendar.HOUR_OF_DAY)+":"+mAddMeetingViewModel.getEndHour().get(Calendar.MINUTE));
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

    // AVAILABLE_ROOM
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
                Toast.makeText(this, "Temps de réunion : " + mReunionHourTimeLeft + "h " + mReunionMinuteTimeLeft, LENGTH_SHORT).show();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listRooms);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerRoom.setAdapter(adapter);
            mSpinnerRoom.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String salleChoisie = listRooms.get(position);
        for (int i = 0; i < mAvailableRooms.size(); i++) {
            if(salleChoisie.equalsIgnoreCase(mAvailableRooms.get(i).getName())) {
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

    private void addNewChip(String participant) {
        if (participant == null || participant.isEmpty()) {
            Toast.makeText(this, "Veuillez ajouter un participant", Toast.LENGTH_LONG).show();
            return;
        }
        if (!isEmailValid(participant)){
            Toast.makeText(this, "Veuillez entrer une adresse mail valide.", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            LayoutInflater inflater = LayoutInflater.from(this);

            // Create a Chip from Layout.
            Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, this.mChipGroupParticipant, false);
            newChip.setText(participant);
            newChip.setTag(participant);

            this.mChipGroupParticipant.addView(newChip);

            newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleChipCloseIconClicked((Chip) v);
                }
            });

            mListParticipant.add(participant);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // User close a Chip.
    private void handleChipCloseIconClicked(Chip chip) {
        ChipGroup parent = (ChipGroup) chip.getParent();
        parent.removeView(chip);

        String deletedChip = (String) chip.getTag();
        Iterator i = mAddMeetingViewModel.getListParticipants().iterator();
        while (i.hasNext()) {
            String participant = (String) i.next();
            if (deletedChip.equalsIgnoreCase(participant)) {
                i.remove();
                break;
            }
        }
    }

    public static void navigate (FragmentActivity activity){
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    public static boolean isEmailValid(CharSequence email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    @OnClick(R.id.activity_add_meeting_btn_createMeeting)
    void addReunion() {

        if (!mAddMeetingViewModel.isSubjectFilled()) {
            mEditSubjectReunion.setError("Veuillez choisir un sujet de réunion.");
            return;
        }
        if (!mAddMeetingViewModel.isStartDateFilled()) {
            mEditDate.setError("Veuillez choisir une date.");
            return;
        }
        if (!mAddMeetingViewModel.isStartHourFilled()) {
            mEditStartHour.setError("Veuillez choisir une heure de début.");
            return;
        }
        if (!mAddMeetingViewModel.isEndHourFilled()) {
            mEditEndHour.setError("Veuillez choisir une heure de fin.");
            return;
        }
        if (!mAddMeetingViewModel.isRoomFilled()) {
            mTextViewRoom.setError("Veuillez choisir une salle.");
            return;
        }
        if (!mAddMeetingViewModel.isListParticipantsFilled()) {
            mEditTextParticipant.setError("Veuillez ajouter un participant.");
            return;
        }
        ExampleMeeting reunion = new ExampleMeeting(System.currentTimeMillis(), mAddMeetingViewModel.getStartDate().getTime(), mAddMeetingViewModel.getStartHour().getTime(), mAddMeetingViewModel.getEndHour().getTime(), mAddMeetingViewModel.getRoom(), mAddMeetingViewModel.getSubject(), mAddMeetingViewModel.getListParticipants());
        mMeetingApiService.createMeeting(reunion);
        finish();
    }
}
