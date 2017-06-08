package uw.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class YourEventList extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_event_list);

        mTextMessage = (TextView) findViewById(R.id.message);
        //  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //   navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void GoEventCreate(View view){
        Intent intent = new Intent(this, EventCreation.class);
        startActivity(intent);
    }

}