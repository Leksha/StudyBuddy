package uw.studybuddy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EventCreation extends AppCompatActivity {

    Calendar dateTime = Calendar.getInstance();
    private TextView textView;

    private Button btn_date;
    private Button btn_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        textView = (TextView) findViewById(R.id.Text_date_time);
        btn_date = (Button) findViewById(R.id.btn_datePicker);
        btn_time = (Button) findViewById((R.id.btn_timePicker));

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


    public void GoYouEventList(View view) {
        Intent intent = new Intent(this, YourEventList.class);
        startActivity(intent);
    }
}
