package uw.studybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import uw.studybuddy.UserProfile.UserInfo;

public class SetUpProfile extends AppCompatActivity {
    private EditText etUsername;
    private EditText etFirstC;
    private EditText etSecondC;
    private EditText etThirdC;
    private EditText etFourthC;
    private EditText etFifthC;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);

        etUsername = (EditText)findViewById(R.id.etUsernameSet);
        etFirstC = (EditText)findViewById(R.id.etFirstC);
        etSecondC = (EditText)findViewById(R.id.etSecondC);
        etThirdC = (EditText)findViewById(R.id.etThirdC);
        etFourthC = (EditText)findViewById(R.id.etFourthC);
        etFifthC = (EditText)findViewById(R.id.etFifthC);
        final Button bWelcome = (Button)findViewById(R.id.bWelcome);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        bWelcome.setOnClickListener(new View.OnClickListener(){ // LOGIN
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String firstC = etFifthC.getText().toString().trim();

                /*final HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("username", username);
                dataMap.put("firstCourse", firstC);*/


                mDatabase.child("name").setValue("Alice");
                Intent loginIntent = new Intent(SetUpProfile.this, HomePage.class);
                SetUpProfile.this.startActivity(loginIntent);
            }
        });
    }
}
