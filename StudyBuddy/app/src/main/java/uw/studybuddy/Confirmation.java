package uw.studybuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Confirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        final EditText etConfirmcode = (EditText)findViewById(R.id.etConfirmCode);
        final Button bConfirm = (Button)findViewById(R.id.bConfirm);
        final TextView tvResendEmail = (TextView)findViewById(R.id.tvResentEmail);

        bConfirm.setOnClickListener(new View.OnClickListener(){ // LOGIN
            @Override
            public void onClick(View v) {
                Intent setProfileIntent = new Intent(Confirmation.this, SetUpProfile.class);
                Confirmation.this.startActivity(setProfileIntent);
            }
        });

    }

    public void sentConfirmEmail(View view) {

       // etConfirmCode

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            startActivity(new Intent(this, RegisterActivity.class));
            return;
        }
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(Confirmation.this, LoginActivity.class));
                }

            }
        });
    }
}
