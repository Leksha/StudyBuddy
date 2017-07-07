package uw.studybuddy.LoginAndRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import uw.studybuddy.MainActivity;
import uw.studybuddy.R;

public class LoginActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        /*
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }*/

        final EditText etUsername = (EditText) findViewById(R.id.etEmailLogin);
        final EditText etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        final Button bLogin = (Button) findViewById(R.id.bSubmit);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterNow);
        final CheckBox cbShowPSD = (CheckBox) findViewById(R.id.cbShowPSD);


        cbShowPSD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        setupDevLoginActivity();


    }

    // Uses the "DEV LOGIN" button on the login page to skip the login
    // and registration process for purposes of testing
    private void setupDevLoginActivity() {
        Button mDevLoginButton = (Button)findViewById(R.id.dev_login_button);
        mDevLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }


    public void GotoReset(View view) {
        Intent intent = new Intent(this, ResetPassword.class);
        startActivity(intent);
    }

    public void Login(View view) {
        final EditText Email = (EditText)findViewById(R.id.etEmailLogin);
        String email = Email.getText().toString() + "@edu.uwaterloo.ca";
        final EditText Password = (EditText)findViewById(R.id.etPasswordLogin);
        String password =  Password.getText().toString();

        if(TextUtils.isEmpty(email)){
            String message = this.getString(R.string.EmptyEmail);
            Email.setHint(message);
            Email.setHintTextColor(getResources().getColor(R.color.errorhint));
            return;
        }
        if(TextUtils.isEmpty(password)){
            String message = this.getString(R.string.EmptyPassword);
            Password.setHint(message);
            Password.setHintTextColor(getResources().getColor(R.color.errorhint));
            return;
        }
        final String message = this.getString(R.string.InvalidLogin);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            TextView Error = (TextView)findViewById(R.id.ErrorLogin);
                            Error.setText(message);
                            Password.setText("");
                            Email.setText("");
                            return;
                            //startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                        } else {
                            User = FirebaseAuth.getInstance().getCurrentUser();
                            if(User.isEmailVerified()) {
                                if(User.getDisplayName() == null){
                                    startActivity(new Intent(LoginActivity.this, SetUpProfile.class));
                                    return;
                                }else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    return;
                                }
                            }else{
                                //if it's not verified, send the email again;
                                SentConfirmation();
                                return;
                            }
                        }
                    }


                });
    }

    public void GotoRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void SentConfirmation() {
        final FirebaseUser user = mAuth.getCurrentUser();
        //final EditText Auther = (EditText) findViewById(R.id.etConfirmationEmail);
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            return;
                        }else{
                            startActivity(new Intent(LoginActivity.this, Confirmation.class));
                            return;
                        }
                    }
                });


    }
}
