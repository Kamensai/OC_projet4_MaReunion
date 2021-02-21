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
import android.text.InputType;
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

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.model.Reunion;

import java.util.Calendar;

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

    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;

    private String[] rooms = {"Mario", "Luigi", "Peach", "DonkeyKong", "Yoshi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                                mEditDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rooms);
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
        this.mButtonShowParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelections();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected User: "+rooms[position] , Toast.LENGTH_SHORT).show();
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

    // @OnClick(R.id.activity_add_reunion_btn_createReunion)
    // void addReunion() {
    //    Reunion reunion = new Reunion(0,)
    // }

    public static void navigate (FragmentActivity activity){
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    private void addNewChip() {
        String keyword = this.mEditTextParticipant.getText().toString();
        if (keyword == null || keyword.isEmpty()) {
            Toast.makeText(this, "Please enter the participants!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            LayoutInflater inflater = LayoutInflater.from(this);

            // Create a Chip from Layout.
            Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, this.mChipGroupParticipant, false);
            newChip.setText(keyword);

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

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // User click on "Show Selections" button.
    private void showSelections()  {
        int count = this.mChipGroupParticipant.getChildCount();

        String s = null;
        for(int i=0;i< count; i++) {
        Chip child = (Chip) this.mChipGroupParticipant.getChildAt(i);

            if(!child.isChecked()) {
                continue;
            }

            if(s == null)  {
                s = child.getText().toString();
            } else {
                s += ", " + child.getText().toString();
            }
        }
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    // User close a Chip.
    private void handleChipCloseIconClicked(Chip chip) {
        ChipGroup parent = (ChipGroup) chip.getParent();
        parent.removeView(chip);
    }

    // Chip Checked Changed
    private void handleChipCheckChanged(Chip chip, boolean isChecked) {
    }
}
