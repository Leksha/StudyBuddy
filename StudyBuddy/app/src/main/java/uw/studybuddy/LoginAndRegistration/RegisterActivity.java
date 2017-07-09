package uw.studybuddy.LoginAndRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


import uw.studybuddy.MainActivity;

import uw.studybuddy.FirebaseInstance;

import uw.studybuddy.R;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser Users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etConfirmationEmail = (EditText)findViewById(R.id.etConfirmationEmail);
        //final EditText etUsername = (EditText)findViewById(R.id.etUsernameReg);
        final EditText etPassword = (EditText)findViewById(R.id.etPasswordReg);
        final Button bSubmit = (Button)findViewById(R.id.bSubmit);
        final CheckBox cbShowPSD = (CheckBox) findViewById(R.id.cbShowPSD);

        cbShowPSD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }

    public void SetupAccount(View view) {

        mAuth = FirebaseAuth.getInstance();
        //final EditText Email = (EditText)findViewById(R.id.etUsernameReg);
        final EditText Password = (EditText)findViewById(R.id.etPasswordReg);
        final EditText Auther = (EditText) findViewById(R.id.etConfirmationEmail);

        final TextView Error = (TextView)findViewById(R.id.RegisterErrorDisplay);
        //final String email = Email.getText().toString();
        final String password = Password.getText().toString();
        final String ConfirmEmail  = Auther.getText().toString() + "@edu.uwaterloo.ca";




        if(TextUtils.isEmpty(Auther.getText().toString())){
            String message = this.getString(R.string.EmptyID) ;
            Auther.setHint(message);
            Auther.setHintTextColor(getResources().getColor(R.color.errorhint));
            return;
        }



        if(TextUtils.isEmpty(password)) {
            String message = this.getString(R.string.EmptyPassword);
            Password.setHint(message);
            Password.setHintTextColor(getResources().getColor(R.color.errorhint));
            return;

        }


        mAuth.createUserWithEmailAndPassword(ConfirmEmail, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Error.setText(getResources().getString(R.string.email_account_already_registered));
                            return;

                        }else{
                            //then do the authermail now
                            //set the displayname here
                            String id = Auther.getText().toString();
                            Update_Display(id);
                            SentConfirmation();
                            //After sending the email
                            //you should change the email address to Email

                            startActivity(new Intent(RegisterActivity.this, Confirmation.class));
                            return;
                        }
                    }
                });

    }

    private void Update_Email(String email) {
        Users = FirebaseAuth.getInstance().getCurrentUser();
        final TextView Error = (TextView)findViewById(R.id.RegisterErrorDisplay);
        Users.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Error.setText(getResources().getString(R.string.email_account_already_registered));
                    //just for testing
                    //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    return;
                }else{
                    //startActivity(new Intent(RegisterActivity.this, Confirmation.class));
                }
            }
        });

    }
    private void Update_Display(String questid){
        Users = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileupdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(questid)
                .build();
        Users.updateProfile(profileupdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                }
            }
        });
    }

    public void SentConfirmation() {
        final FirebaseUser user = FirebaseInstance.getFirebaseAuthInstance().getCurrentUser();
        final String message_send = this.getString(R.string.Send_Confirmation);
        final EditText Auther = (EditText) findViewById(R.id.etConfirmationEmail);
        String confirm = Auther.getText().toString();

        final TextView Error = (TextView)findViewById(R.id.RegisterErrorDisplay);

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            String message = getResources().getString(R.string.InvalidEmail);
                            Error.setText(message);
                            return;
                        }else {
                            Error.setText(message_send);
                            return;
                        }
                    }
                });


    }

}
