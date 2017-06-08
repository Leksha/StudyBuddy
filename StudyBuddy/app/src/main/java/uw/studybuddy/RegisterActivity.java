package uw.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etConfirmationEmail = (EditText)findViewById(R.id.etConfirmationEmail);
        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);
        final Button bSubmit = (Button)findViewById(R.id.bSubmit);

        bSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ConfirmationIntent = new Intent(RegisterActivity.this, Confirmation.class);
                // create intent to open a registerActivity
                RegisterActivity.this.startActivity(ConfirmationIntent);
                // tell the current activity to perform that intent
            }
        });

    }
}
