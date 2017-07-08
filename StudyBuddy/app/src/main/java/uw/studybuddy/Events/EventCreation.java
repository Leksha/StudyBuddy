package uw.studybuddy.Events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

import uw.studybuddy.FirebaseInstance;
import uw.studybuddy.MainActivity;
import uw.studybuddy.R;

public class EventCreation extends AppCompatActivity {

    Calendar dateTime = Calendar.getInstance();
    private TextView textView;
    private TextView courseCreate;
    private TextView descriptionCreate;
    private TextView locationCreate;
    private TextView subjectCreate;

    private Button btn_date;
    private Button btn_time;
    private Button mConfirm;
    //private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        courseCreate = (EditText)findViewById(R.id.course_create);
        locationCreate = (EditText)findViewById(R.id.location_create);
        subjectCreate = (EditText)findViewById(R.id.subject_create);
        descriptionCreate = (EditText)findViewById(R.id.description_create);

        textView = (TextView) findViewById(R.id.Text_date_time);
        btn_date = (Button) findViewById(R.id.btn_datePicker);
        btn_time = (Button) findViewById((R.id.btn_timePicker));

        mConfirm = (Button)findViewById(R.id.btn_confirm_event_setup);
        //progressDialog = new ProgressDialog(this);

        btn_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateDate();
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateTime();
            }
        });

        updateTextLable();


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                courseCreate = (EditText) findViewById(R.id.course_create);
                locationCreate = (EditText) findViewById(R.id.location_create);
                subjectCreate = (EditText) findViewById(R.id.subject_create);
                descriptionCreate = (EditText) findViewById(R.id.description_create);

                String course = courseCreate.getText().toString().trim();
                String description = descriptionCreate.getText().toString().trim();
                String location = locationCreate.getText().toString().trim();

                String subject = subjectCreate.getText().toString().trim();
/*
        if(!TextUtils.isEmpty(course) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(subject)){
            dataMap.put("course", course);
            dataMap.put("description", description);
            dataMap.put("location", location);
            dataMap.put("subject", subject);


            mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(EventCreation.this, "Saving information...",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EventCreation.this, "Error occured.",Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            Toast.makeText(EventCreation.this, "You have unfilled blank.",Toast.LENGTH_LONG).show();
        }*/

                String title = subjectCreate.getText().toString().trim();

                //create a new event, add to firebase
                boolean eventCreationSuccess = FirebaseInstance.addNewEventToDatabase(course, title, location, description);
                //on successful creation of the event: toast message
                if (eventCreationSuccess) {
                    Toast.makeText(EventCreation.this, "Saving information...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EventCreation.this, "Error occured.", Toast.LENGTH_LONG).show();
                }


                finish();
            }
        });
    }

    private void updateDate(){
        new DatePickerDialog(this, d , dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayofMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayofMonth);
            updateTextLable();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            dateTime.set(Calendar.HOUR_OF_DAY, i);
            dateTime.set(Calendar.MINUTE, i1);
            updateTextLable();
        }
    };

    private void updateTextLable() {
        textView.setText(dateTime.get(Calendar.YEAR) + " / " + dateTime.get(Calendar.MONTH) + " / "+ dateTime.get(Calendar.DAY_OF_MONTH)
                + "  " + dateTime.get(Calendar.HOUR_OF_DAY) + " : " + dateTime.get(Calendar.MINUTE));
    }


}

