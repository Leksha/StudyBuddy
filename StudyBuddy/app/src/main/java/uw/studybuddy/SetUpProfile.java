package uw.studybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetUpProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);

        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final Button bWelcome = (Button)findViewById(R.id.bWelcome);

        bWelcome.setOnClickListener(new View.OnClickListener(){ // LOGIN
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SetUpProfile.this, NavigationPane.class);
                SetUpProfile.this.startActivity(loginIntent);
            }
        });
    }
}
