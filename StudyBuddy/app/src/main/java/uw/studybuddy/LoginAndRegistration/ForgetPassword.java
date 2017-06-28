package uw.studybuddy.LoginAndRegistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uw.studybuddy.R;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    public void ToResetPage(View view) {
        Intent intent = new Intent(this, ResetPassword.class);
        startActivity(intent);
    }
}
