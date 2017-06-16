package uw.studybuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.text.Editable;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import uw.studybuddy.UserProfile.UserInfo;
import uw.studybuddy.UserProfile.UserProfileActivity;

public class LoginActivity extends AppCompatActivity{
    private EditText etUsername;
    private EditText etPassword;
    private Button bLogin;
    private TextView registerLink;
    private CheckBox cbShowPS

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, HomePage.class));
        }

        final EditText etUsername = (EditText) findViewById(R.id.etEmailLogin);
        final EditText etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        final Button bLogin = (Button) findViewById(R.id.bSubmit);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterNow);
        final CheckBox cbShowPSD = (CheckBox) findViewById(R.id.cbShowPSD);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLogin = (Button)findViewById(R.id.bSubmit);
        registerLink = (TextView)findViewById(R.id.tvRegisterNow);
        cbShowPSD = (CheckBox) findViewById(R.id.cbShowPSD);

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



    }


    public void GotoReset(View view) {
        Intent intent = new Intent(this, ResetPassword.class);
        startActivity(intent);
    }

    public void Login(View view) {
        EditText Email = (EditText)findViewById(R.id.etEmailLogin);
        String email = Email.getText().toString();
        EditText Password = (EditText)findViewById(R.id.etPasswordLogin);
        String password =  Password.getText().toString();


        if(TextUtils.isEmpty(email)){
            startActivity(new Intent(LoginActivity.this, EventCreation.class));
        }
        if(TextUtils.isEmpty(password)){
            startActivity(new Intent(LoginActivity.this, EventCreation.class));
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, HomePage.class));
                        }
                    }


                });
    }

    public void GotoRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
