package uw.studybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class YourEventList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_event_list);
    }
    public void CreatEvent(View view) {
        Intent intent = new Intent(this, EventCreation.class);
        startActivity(intent);
    }
}
