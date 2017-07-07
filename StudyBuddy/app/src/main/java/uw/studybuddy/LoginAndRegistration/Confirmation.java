package uw.studybuddy.LoginAndRegistration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uw.studybuddy.MainActivity;
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

        Button bConfirm = (Button) findViewById(R.id.bConfirm);
        TextView tvResendEmail = (TextView) findViewById(R.id.tvResentEmail);


        bConfirm.setOnClickListener(new View.OnClickListener() { // LOGIN
            @Override
            public void onClick(View v) {
                Intent setProfileIntent = new Intent(Confirmation.this, LoginActivity.class);
                Confirmation.this.startActivity(setProfileIntent);
                return;

            }
        });

    }
}
