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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private Button btn_LaunchMap;
    //private ProgressDialog progressDialog;

    private String mPostKey = null;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabase;

    int PLACE_PICKER_REQUEST = 1;


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
        btn_LaunchMap = (Button) findViewById(R.id.btn_launchmap);

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

        //mPostKey = getIntent().getExtras().getString("event_id");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");

        //mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getEmail()); email -14 quest id


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


                String title = subjectCreate.getText().toString().trim();
                String uid = mCurrentUser.getUid();
                String email = mCurrentUser.getEmail();
                String questId = email.substring(0, email.length()-17);
                String date = (Integer.toString(dateTime.get(Calendar.YEAR)) + " / " + Integer.toString(dateTime.get(Calendar.MONTH)) +
                        " / " + Integer.toString(dateTime.get(Calendar.DAY_OF_MONTH)));
                String time = (Integer.toString(dateTime.get(Calendar.HOUR_OF_DAY)) + " : " + Integer.toString(dateTime.get(Calendar.MINUTE)));

                //create a new event, add to firebase
                boolean eventCreationSuccess = FirebaseInstance.addNewEventToDatabase(course, title, location, description,
                        uid, questId, date, time);
                //on successful creation of the event: toast message
                if (eventCreationSuccess) {
                    Toast.makeText(EventCreation.this, "Saving information...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EventCreation.this, "Error occured.", Toast.LENGTH_LONG).show();
                }


                finish();
            }
        });

        btn_LaunchMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mDatabase.child(mPostKey).addValueEventListener(new ValueEventListener() {
                    //@Override
                    //public void onDataChange(DataSnapshot dataSnapshot) {
                        //int PLACE_PICKER_REQUEST = 199;
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        Intent intent;
                        try {
                            intent = builder.build(EventCreation.this);
                            startActivityForResult(intent, PLACE_PICKER_REQUEST);

                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        }
                        //}
                    }

                   // @Override
                    //public void onCancelled(DatabaseError databaseError) {

                    //}
                //});
            //}
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PLACE_PICKER_REQUEST) {
            if(resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String address = String.format("Place: %s",place.getName());
                locationCreate.setText(address);
            }
        }
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

