package uw.studybuddy.LoginAndRegistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uw.studybuddy.R;

public class Confirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        final Button bConfirm = (Button) findViewById(R.id.bConfirm);
        final TextView tvResendEmail = (TextView) findViewById(R.id.tvResentEmail);

        bConfirm.setOnClickListener(new View.OnClickListener() { // LOGIN
            @Override
            public void onClick(View v) {
                Intent setProfileIntent = new Intent(Confirmation.this, SetUpProfile.class);
                Confirmation.this.startActivity(setProfileIntent);
            }
        });

    }
}