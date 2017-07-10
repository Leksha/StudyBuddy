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

import uw.studybuddy.FirebaseInstance;
import uw.studybuddy.MainActivity;
import uw.studybuddy.R;

public class LoginActivity extends AppCompatActivity{

    private String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final FirebaseUser user = FirebaseInstance.getFirebaseAuthInstance().getCurrentUser();
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

                String email = "q73chen";
                String password = "123qwe";

                EditText etUsername = (EditText) findViewById(R.id.etEmailLogin);
                EditText etPassword = (EditText) findViewById(R.id.etPasswordLogin);
                Button bLogin = (Button) findViewById(R.id.bSubmit);

                etUsername.setText(email);
                etPassword.setText(password);
                bLogin.callOnClick();
            }
        });
    }


    public void GotoReset(View view) {
        Intent intent = new Intent(this, ResetPassword.class);
        startActivity(intent);
    }

    public void Login(View view) {
        Log.d(TAG, "Login called");
        final EditText Email = (EditText)findViewById(R.id.etEmailLogin);
        final String email = Email.getText().toString();
        final EditText Password = (EditText)findViewById(R.id.etPasswordLogin);
        final String password =  Password.getText().toString();


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
        FirebaseInstance.getFirebaseAuthInstance().signInWithEmailAndPassword(email, password)
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
                            Log.d(TAG, "email: " + email);
                            Log.d(TAG, "password: " + password);

                            Intent homeActivity = new Intent(LoginActivity.this, MainActivity.class);
                            homeActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeActivity);
                        }
                    }


                });
    }

    public void GotoRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
