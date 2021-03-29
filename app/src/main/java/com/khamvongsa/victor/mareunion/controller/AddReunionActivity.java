package com.khamvongsa.victor.mareunion.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.di.DI;
import com.khamvongsa.victor.mareunion.model.Reunion;
import com.khamvongsa.victor.mareunion.model.Salle;
import com.khamvongsa.victor.mareunion.service.FakeReunionApiService;
import com.khamvongsa.victor.mareunion.service.FakeSalle;
import com.khamvongsa.victor.mareunion.service.ReunionApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class AddReunionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // UI COMPONENTS
    @BindView(R.id.activity_add_reunion_inputDate)
    TextInputLayout mDateInput;
    @BindView(R.id.activity_add_reunion_editDate)
    EditText mEditDate;
    @BindView(R.id.activity_add_reunion_btnGetDate)
    Button mButtonGetDate;
    @BindView(R.id.activity_add_reunion_textViewDate)
    TextView mTextViewDate;

    @BindView(R.id.activity_add_reunion_inputHour)
    TextInputLayout mHourInput;
    @BindView(R.id.activity_add_reunion_editHour)
    EditText mEditHour;
    @BindView(R.id.activity_add_reunion_btnGetHour)
    Button mButtonGetHour;
    @BindView(R.id.activity_add_reunion_textViewHour)
    TextView mTextViewHour;

    @BindView(R.id.activity_add_reunion_spinnerRoom)
    Spinner mSpinnerRoom;

    @BindView(R.id.activity_add_reunion_SubjectReunion)
    TextInputLayout mInputSubjectReunion;

    @BindView(R.id.activity_add_reunion_editText_participant)
    EditText mEditTextParticipant;
    @BindView(R.id.activity_add_reunion_chipGroup)
    ChipGroup mChipGroupParticipant;
    @BindView(R.id.activity_add_reunion_btnAdd_participant)
    Button mButtonAddParticipant;
    @BindView(R.id.activity_add_reunion_btn_showParticipant)
    Button mButtonShowParticipant;

    @BindView(R.id.activity_add_reunion_btn_createReunion)
    MaterialButton addButton;

    private ReunionApiService mReunionApiService;
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;
    private List<String> mlistParticipant = new ArrayList<String>();
    private Calendar mStartDate = Calendar.getInstance();
    private Calendar mStartHour = Calendar.getInstance();
    private ExempleSalle mSalle;

    private List<ExempleSalle> mSallesDisponibles;
    private List<String> listSalles = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mReunionApiService = DI.getReunionApiService();
        init();

        // DATE
        mEditDate.setInputType(InputType.TYPE_NULL);
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
                                mTextViewDate.setText("Selected Date: " + mEditDate.getText());
                            }
                        }, year, month, day);
                mDatePicker.show();


            }
        });
        mButtonGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewDate.setText("Selected Date: " + mEditDate.getText());
            }
        });

        //HOUR
        mEditHour.setInputType(InputType.TYPE_NULL);
        mEditHour.setOnClickListener(new View.OnClickListener() {
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
                                mEditHour.setText(sHour + ":" + sMinute);
                                mStartHour.set(0,0,0,sHour,sMinute);
                                mTextViewHour.setText("Selected Time: " + mEditHour.getText());
                            }
                        }, hour, minutes, true);
                mTimePicker.show();
            }
        });
        mButtonGetHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewHour.setText("Selected Time: " + mEditHour.getText());
            }
        });

        // AVAILABLE_ROOM
        mSallesDisponibles = mReunionApiService.getSalles();
        for (int i = 0; i < mSallesDisponibles.size(); i++) {
            String salle = mSallesDisponibles.get(i).getNom();
            listSalles.add(salle);
            }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSalles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRoom.setAdapter(adapter);
        mSpinnerRoom.setOnItemSelectedListener(this);

        // REUNION SUBJECT

        // PARTICIPANTS
        this.mButtonAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewChip();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected User: "+ listSalles.get(position) , Toast.LENGTH_SHORT).show();
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



    public static void navigate (FragmentActivity activity){
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    private void addNewChip() {
        String participant = this.mEditTextParticipant.getText().toString();
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
            //
            // Other methods:
            //
            // newChip.setCloseIconVisible(true);
            // newChip.setCloseIconResource(R.drawable.your_icon);
            // newChip.setChipIconResource(R.drawable.your_icon);
            // newChip.setChipBackgroundColorResource(R.color.red);
            // newChip.setTextAppearanceResource(R.style.ChipTextStyle);
            // newChip.setElevation(15);

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

    // TODO : L'utilisateur doit remplir tous les champs pour pouvoir créer la réunion

    private void init() {

        mInputSubjectReunion.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                addButton.setEnabled(s.length() > 0);
            }
        });
    }

    @OnClick(R.id.activity_add_reunion_btn_createReunion)
    void addReunion() {
        ExempleReunion reunion = new ExempleReunion(
                System.currentTimeMillis(), mStartDate.getTime(), mSalle, mInputSubjectReunion.getEditText().getText().toString(), mlistParticipant
        );
        mReunionApiService.createReunion(reunion);
        finish();
    }
}
