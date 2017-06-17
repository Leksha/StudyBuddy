package uw.studybuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }


    public void SentResetEmail(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        final EditText Email = (EditText)findViewById(R.id.ResetEmail);
        String email = Email.getText().toString();
        final String message = this.getString(R.string.EmptyEmail);

        final TextView Error = (TextView)findViewById(R.id.resentError);
        if(TextUtils.isEmpty(email)){
            Email.setHint(message);
            Email.setHintTextColor(getResources().getColor(R.color.errorhint));
            return;
        }
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(ResetPassword.this, LoginActivity.class));
                        }else{
                            Error.setText(getResources().getString(R.string.Email_does_not_exist));
                            return;
                        }
                    }
                });
    }

}
