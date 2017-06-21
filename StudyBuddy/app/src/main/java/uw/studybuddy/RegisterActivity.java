package uw.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etConfirmationEmail = (EditText)findViewById(R.id.etConfirmationEmail);
        final EditText etUsername = (EditText)findViewById(R.id.etUsernameReg);
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
        EditText Email = (EditText)findViewById(R.id.etUsernameReg);
        EditText Password = (EditText)findViewById(R.id.etPasswordReg);
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                        }else{
                            startActivity(new Intent(RegisterActivity.this, SetUpProfile.class));
                        }
                    }
                });
    }

}
