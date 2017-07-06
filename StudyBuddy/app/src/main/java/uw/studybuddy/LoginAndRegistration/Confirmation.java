package uw.studybuddy.LoginAndRegistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uw.studybuddy.FirebaseInstance;
import uw.studybuddy.R;

public class Confirmation extends AppCompatActivity {

    //DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
   // DatabaseReference mUsersRef  = mRootRef.child("Users");
    //DatabaseReference mAccountRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        final Button bConfirm = (Button) findViewById(R.id.bConfirm);
        final TextView tvResendEmail = (TextView) findViewById(R.id.tvResentEmail);

        bConfirm.setOnClickListener(new View.OnClickListener() { // LOGIN
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseInstance.getFirebaseAuthInstance().getCurrentUser();

                //mAccountRef = mUsersRef.child(user.toString());
                Intent setProfileIntent = new Intent(Confirmation.this, SetUpProfile.class);
                Confirmation.this.startActivity(setProfileIntent);
            }
        });

    }
}
