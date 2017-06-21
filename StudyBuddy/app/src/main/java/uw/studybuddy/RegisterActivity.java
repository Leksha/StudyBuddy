package uw.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //final EditText etConfirmationEmail = (EditText)findViewById(R.id.etConfirmationEmail);
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
        final EditText Email = (EditText)findViewById(R.id.etUsernameReg);
        EditText Password = (EditText)findViewById(R.id.etPasswordReg);
        //EditText Auther = (EditText) findViewById(R.id.etConfirmationEmail) ;
        final TextView Error = (TextView)findViewById(R.id.RegisterErrorDisplay);
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        //final String Authemail = Auther.getText().toString();
        if(TextUtils.isEmpty(email)){
            String message = this.getString(R.string.EmptyEmail);
            Email.setHint(message);
            Email.setHintTextColor(getResources().getColor(R.color.errorhint));
            return;
        }
        if(TextUtils.isEmpty(password)) {
            String message = this.getString(R.string.EmptyPassword);
            Password.setHint(message);
            Password.setHintTextColor(getResources().getColor(R.color.errorhint));
            return;

        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Error.setText(getResources().getString(R.string.email_account_already_registered));
                            return;

                        }else{
                            //then do the authermail now
                            SentConfirmation();

                        }
                    }
                });
    }

    public void SentConfirmation() {
        final FirebaseUser user = mAuth.getCurrentUser();
        final String message_send = this.getString(R.string.Send_Confirmation);
        //final EditText Auther = (EditText) findViewById(R.id.etConfirmationEmail) ;
        final TextView Error = (TextView)findViewById(R.id.RegisterErrorDisplay);
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            String message = getResources().getString(R.string.InvalidEmail);
                            Error.setText(message);
                            return;
                        }else{
                            Error.setText(message_send);
                            startActivity(new Intent(RegisterActivity.this, Confirmation.class));
                        }
                    }
                });


    }

}
