package com.khamvongsa.victor.mareunion.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.di.DI;
import com.khamvongsa.victor.mareunion.service.CalculTime;
import com.khamvongsa.victor.mareunion.service.CalculTimeService;
import com.khamvongsa.victor.mareunion.service.FakeReunionApiService;
import com.khamvongsa.victor.mareunion.service.ReunionApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;

public class AddReunionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // UI COMPONENTS
    @BindView(R.id.activity_add_reunion_inputDate)
    TextInputLayout mDateInput;
    @BindView(R.id.activity_add_reunion_editDate)
    EditText mEditDate;

    // Start Hour Reunion
    @BindView(R.id.activity_add_reunion_inputStartHour)
    TextInputLayout mStartHourInput;
    @BindView(R.id.activity_add_reunion_editStartHour)
    EditText mEditStartHour;

    // End Hour Reunion
    @BindView(R.id.activity_add_reunion_inputEndHour)
    TextInputLayout mEndHourInput;
    @BindView(R.id.activity_add_reunion_editEndHour)
    EditText mEditEndHour;

    @BindView(R.id.activity_add_reunion_spinnerRoom)
    Spinner mSpinnerRoom;

    @BindView(R.id.activity_add_reunion_SubjectReunion)
    TextInputLayout mInputSubjectReunion;
    @BindView(R.id.activity_add_reunion_ReunionName)
    EditText mEditSubjectReunion;

    @BindView(R.id.activity_add_reunion_editText_participant)
    EditText mEditTextParticipant;
    @BindView(R.id.activity_add_reunion_chipGroup)
    ChipGroup mChipGroupParticipant;
    @BindView(R.id.activity_add_reunion_btnAdd_participant)
    Button mButtonAddParticipant;

    @BindView(R.id.activity_add_reunion_btn_createReunion)
    MaterialButton addButton;


    private ReunionApiService mReunionApiService;
    private CalculTimeService mCalculTimeService;
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;
    private List<String> mlistParticipant = new ArrayList<String>();
    private Calendar mStartDate = Calendar.getInstance();
    private Calendar mStartHour = Calendar.getInstance();
    private Calendar mEndHour = Calendar.getInstance();
    private ExempleSalle mSalle;
    private List<ExempleReunion> mReunions;

    private List<ExempleSalle> mSallesDisponibles;
    private List<String> listSalles = new ArrayList<String>();

    private final static String TAG = AddReunionActivity.class.getSimpleName();
    private final static String REUNION_SUBJECT = "REUNION_SUBJECT";
    private final static String DATE = "DATE";
    private final static String DATE_YEAR = "DATE_YEAR";
    private final static String DATE_MONTH = "DATE_MONTH";
    private final static String DATE_DAY = "DATE_DAY";
    private final static String START_TIME = "START_TIME";
    private final static String START_HOUR = "START_HOUR";
    private final static String START_MINUTE = "START_MINUTE";
    private final static String END_TIME = "END_TIME";
    private final static String END_HOUR = "END_HOUR";
    private final static String END_MINUTE = "END_MINUTE";
    private final static String PARTICIPANT = "PARTICIPANT";
    private final static String LIST_PARTICIPANT = "LIST_PARTICIPANT";
    private final static String CHOSEN_ROOM = "CHOSEN_ROOM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mReunionApiService = DI.getReunionApiService();
        mCalculTimeService = new CalculTime();

        // DATE
        mEditDate.setInputType(InputType.TYPE_NULL);
        dateStart();


        //HOUR START
        mEditStartHour.setInputType(InputType.TYPE_NULL);
        hourStart();


        // HOUR END
        mEditEndHour.setInputType(InputType.TYPE_NULL);
        endHour();

        // REUNION SUBJECT

        // PARTICIPANTS
        this.mButtonAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewChip(mEditTextParticipant.getText().toString());
            }
        });
    }

    public void dateStart() {
        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                mDatePicker = new DatePickerDialog(AddReunionActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mStartDate.set(year,monthOfYear, dayOfMonth);
                                mEditDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });
    }
    public void hourStart() {
        mEditStartHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                mTimePicker = new TimePickerDialog(AddReunionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                mEditStartHour.setText(sHour + ":" + sMinute);
                                mStartHour.set(0, 0, 0, sHour, sMinute);
                                if (sHour < hour
                                        && (mStartDate.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                                        && (mStartDate.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH))
                                        && (mStartDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))
                                    || (sHour == hour && sMinute < minutes)
                                        && (mStartDate.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                                        && (mStartDate.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH))
                                        && (mStartDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)))  {
                                    mEditStartHour.setError("This time is already passed. Provide a positive hour please.");
                                }
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
        mEditEndHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                mTimePicker = new TimePickerDialog(AddReunionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                int mStartReunionHourChosen = mStartHour.get(Calendar.HOUR_OF_DAY);
                                int mStartReunionMinuteChosen = mStartHour.get(Calendar.MINUTE);
                                mEditEndHour.setText(sHour + ":" + sMinute);
                                mEndHour.set(0,0,0,sHour,sMinute);
                                if (mStartReunionHourChosen > sHour || (mStartReunionHourChosen == sHour && mStartReunionMinuteChosen > sMinute)) {
                                    mEditEndHour.setError("You can't end before the start of the meeting. Provide another hour please.");
                                }
                                else {
                                    mEditEndHour.setError(null);
                                }
                                availableRoom();
                            }
                        }, hour, minutes, true);
                mTimePicker.show();
            }
        });
    }
    // TODO : Conserver les données déjà choisies, et changer de layout (portrait, paysage) Date et Heures*

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putString(REUNION_SUBJECT, mEditSubjectReunion.getText().toString());

        out.putString(DATE, mEditDate.getText().toString());
        out.putInt(DATE_YEAR, mStartDate.get(Calendar.YEAR));
        out.putInt(DATE_MONTH, mStartDate.get(Calendar.MONTH));
        out.putInt(DATE_DAY, mStartDate.get(Calendar.DAY_OF_MONTH));

        out.putString(START_TIME, mEditStartHour.getText().toString());
        out.putInt(START_HOUR, mStartHour.get(Calendar.HOUR_OF_DAY));
        out.putInt(START_MINUTE, mStartHour.get(Calendar.MINUTE));

        out.putString(END_TIME, mEditEndHour.getText().toString());
        out.putInt(END_HOUR, mEndHour.get(Calendar.HOUR_OF_DAY));
        out.putInt(END_MINUTE, mEndHour.get(Calendar.MINUTE));

        out.putInt(CHOSEN_ROOM, (int)mSalle.getId());

        out.putString(PARTICIPANT, mEditTextParticipant.getText().toString());
        out.putStringArrayList(LIST_PARTICIPANT, new ArrayList<>(mlistParticipant));


        Log.d(TAG, "############################################");
        Log.d(TAG, "Info onSave :");
        Log.d(TAG, "ReunionSubject: " + mEditSubjectReunion.getText().toString());
        Log.d(TAG, "StartDate: " + mEditDate.getText().toString());
        Log.d(TAG, "StartHour: " + mEditStartHour.getText().toString());
        Log.d(TAG, "EndHour: " + mEditEndHour.getText().toString());
        Log.d(TAG, "Participant: " + mEditTextParticipant.getText().toString());
        Log.d(TAG, "############################################");
    }

    @Override
    public void onRestoreInstanceState(Bundle in) {
        super.onRestoreInstanceState(in);
        mEditSubjectReunion.setText(in.getString(REUNION_SUBJECT));
        mEditDate.setText(in.getString(DATE));
        mStartDate.set(in.getInt(DATE_YEAR), in.getInt(DATE_MONTH), in.getInt(DATE_DAY));
        mEditStartHour.setText(in.getString(START_TIME));
        mStartHour.set(0,0,0,in.getInt(START_HOUR), in.getInt(START_MINUTE));
        mEditEndHour.setText(in.getString(END_TIME));
        mEndHour.set(0,0,0,in.getInt(END_HOUR), in.getInt(END_MINUTE));
        availableRoom();
        mSpinnerRoom.setSelection(in.getInt(CHOSEN_ROOM));
        List<String> newListParticipant = in.getStringArrayList(LIST_PARTICIPANT);
        for (int i = 0; i < newListParticipant.size(); i++) {
            addNewChip(newListParticipant.get(i));
        }
        mEditTextParticipant.setText(in.getString(PARTICIPANT));

        Log.d(TAG, "############################################");
        Log.d(TAG, "Info onRestore:");
        Log.d(TAG, "ReunionSubject: " + mEditSubjectReunion.getText().toString());
        Log.d(TAG, "StartDate: " + mEditDate.getText().toString());
        Log.d(TAG, "StartHour: " + mEditStartHour.getText().toString());
        Log.d(TAG, "EndHour: " + mEditEndHour.getText().toString());
        Log.d(TAG, "Participant: " + mEditTextParticipant.getText().toString());
        Log.d(TAG, "############################################");
    }

    // AVAILABLE_ROOM
    public void availableRoom() {
        int mStartReunionMinuteChosen = mStartHour.get(Calendar.MINUTE);
        int mEndReunionMinuteChosen = mEndHour.get(Calendar.MINUTE);
        int mReunionHourTime = mEndHour.get(Calendar.HOUR_OF_DAY) - mStartHour.get(Calendar.HOUR_OF_DAY);
        int mReunionMinuteTime = (mEndHour.get(Calendar.HOUR_OF_DAY)*60 + mEndHour.get(Calendar.MINUTE)) - (mStartHour.get(Calendar.HOUR_OF_DAY)*60 + mStartHour.get(Calendar.MINUTE));

        int mReunionMinuteTimeLeft = mCalculTimeService.calculMinuteLeft(mReunionMinuteTime, mReunionHourTime,mStartReunionMinuteChosen, mEndReunionMinuteChosen);
        int mReunionHourTimeLeft = mCalculTimeService.calculHourLeft(mReunionMinuteTime, mReunionHourTime,mStartReunionMinuteChosen, mEndReunionMinuteChosen);

        mReunions = mReunionApiService.getReunions();
        mSallesDisponibles = mReunionApiService.getSalles();
        listSalles = mReunionApiService.FilterAvailableRooms(mReunions, mSallesDisponibles, mStartDate, mStartHour, mEndHour);

        if ((mReunionHourTimeLeft == 0 && mReunionMinuteTimeLeft > 0)
                || (mReunionHourTimeLeft > 0 && mReunionMinuteTimeLeft > 0)
                || (mReunionHourTimeLeft > 0 && mReunionMinuteTimeLeft == 0) ) {
            Toast.makeText(this, "Temps de réunion : " + mReunionHourTimeLeft + "h " + mReunionMinuteTimeLeft, LENGTH_SHORT).show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSalles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRoom.setAdapter(adapter);
        mSpinnerRoom.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String salleChoisie = listSalles.get(position);
        for (int i = 0; i < mSallesDisponibles.size(); i++) {
            if(salleChoisie.equalsIgnoreCase(mSallesDisponibles.get(i).getNom())) {
                mSalle = mSallesDisponibles.get(i);
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
            Toast.makeText(this, "Please enter the participants!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            LayoutInflater inflater = LayoutInflater.from(this);

            // Create a Chip from Layout.
            Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, this.mChipGroupParticipant, false);
            newChip.setText(participant);
            newChip.setTag(participant);

            this.mChipGroupParticipant.addView(newChip);

            // Set Listener for the Chip:
            newChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    handleChipCheckChanged((Chip) buttonView, isChecked);
                }
            });

            newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleChipCloseIconClicked((Chip) v);
                }
            });

            this.mEditTextParticipant.setText("");
            mlistParticipant.add(participant);

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
        Iterator i = mlistParticipant.iterator();
        while (i.hasNext()) {
            String participant = (String) i.next();
            if (deletedChip.equalsIgnoreCase(participant)) {
                i.remove();
                break;
            }
        }
    }

    // Chip Checked Changed
    private void handleChipCheckChanged(Chip chip, boolean isChecked) {
    }


    public static void navigate (FragmentActivity activity){
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @OnClick(R.id.activity_add_reunion_btn_createReunion)
    void addReunion() {
        String debut = mEditDate.getText().toString();
        String startHour = mEditStartHour.getText().toString();
        String endHour = mEditEndHour.getText().toString();
        ExempleSalle salle = mSalle;
        // TODO : Est-ce que ça marche pour l'objet "salle"?
        String sujet = mEditSubjectReunion.getText().toString();
        List<String> participants = mlistParticipant;

        if (debut.trim().isEmpty() || startHour.trim().isEmpty()
                || endHour.trim().isEmpty() || salle.toString().trim().isEmpty()
                || sujet.trim().isEmpty() || participants.size() == 0) {
            Toast.makeText(this,"Please fill all the input fields. ", Toast.LENGTH_LONG).show();
        }
        else {ExempleReunion reunion = new ExempleReunion(System.currentTimeMillis(), mStartDate.getTime(), mStartHour.getTime(),mEndHour.getTime(), mSalle, mEditSubjectReunion.getText().toString(), mlistParticipant);
            mReunionApiService.createReunion(reunion);
            finish();
        }
    }
}
