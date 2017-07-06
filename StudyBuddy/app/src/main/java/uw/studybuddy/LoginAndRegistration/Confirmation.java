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

import uw.studybuddy.R;

public class Confirmation extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser User;
    private TextView Noti;
    int test = 1;


    //DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
   // DatabaseReference mUsersRef  = mRootRef.child("Users");
    //DatabaseReference mAccountRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Noti = (TextView) findViewById(R.id.tvResentEmail);
        mAuth = FirebaseAuth.getInstance();
        User = FirebaseAuth.getInstance().getCurrentUser();

        final Button bConfirm = (Button) findViewById(R.id.bConfirm);
        final TextView tvResendEmail = (TextView) findViewById(R.id.tvResentEmail);


        bConfirm.setOnClickListener(new View.OnClickListener() { // LOGIN
            @Override
            public void onClick(View v) {

                User = FirebaseAuth.getInstance().getCurrentUser();

                Noti.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                if (User.isEmailVerified()) {
                    //mAccountRef = mUsersRef.child(user.toString());
                    Intent setProfileIntent = new Intent(Confirmation.this, SetUpProfile.class);
                    Confirmation.this.startActivity(setProfileIntent);
                    return;
                } else {
                    Noti.setTextColor(getResources().getColor(R.color.errorhint));
                    bConfirm.setClickable(true);
                    return;
                }
            }
        });

    }
}
