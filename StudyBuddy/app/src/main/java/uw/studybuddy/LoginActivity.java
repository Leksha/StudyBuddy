package uw.studybuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uw.studybuddy.UserProfile.UserInfo;
import uw.studybuddy.UserProfile.UserProfileActivity;

public class LoginActivity extends AppCompatActivity{
    private EditText etUsername;
    private EditText etPassword;
    private Button bLogin;
    private TextView registerLink;
    private CheckBox cbShowPSD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLogin = (Button)findViewById(R.id.bSubmit);
        registerLink = (TextView)findViewById(R.id.tvRegisterNow);
        cbShowPSD = (CheckBox) findViewById(R.id.cbShowPSD);

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

        bLogin.setOnClickListener(new View.OnClickListener(){ // LOGIN
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginActivity.this, HomePage.class);
                LoginActivity.this.startActivity(loginIntent);
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener(){ // REGISTER NOW
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                // create intent to open a registerActivity
                LoginActivity.this.startActivity(registerIntent);
                // tell the current activity to perform that intent
            }
        });
    }

    public void GotoReset(View view) {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
    }

}
