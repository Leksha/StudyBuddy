package uw.studybuddy.LoginAndRegistration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import uw.studybuddy.CourseInfo;
import uw.studybuddy.FirebaseInstance;
import uw.studybuddy.MainActivity;
import uw.studybuddy.R;
import uw.studybuddy.UserProfile.UserInfo;
import uw.studybuddy.UserProfile.dummy.FirebaseUserInfo;
import uw.studybuddy.UserProfile.dummy.UserPattern;

public class SetUpProfile extends AppCompatActivity {
    private EditText etUsername;
    private EditText etFirstC;
    private EditText etSecondC;
    private EditText etThirdC;
    private EditText etFourthC;
    private EditText etFifthC;
    //private DatabaseReference mDatabase;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    //private FirebaseAuth mAuth;
    private FirebaseUser User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);

        //User = FirebaseAuth.getInstance().getCurrentUser();
        User = FirebaseInstance.getFirebaseAuthInstance().getCurrentUser();

        etUsername = (EditText)findViewById(R.id.etUsernameSet);
        etFirstC = (EditText)findViewById(R.id.etFirstC);
        etSecondC = (EditText)findViewById(R.id.etSecondC);
        etThirdC = (EditText)findViewById(R.id.etThirdC);
        etFourthC = (EditText)findViewById(R.id.etFourthC);
        etFifthC = (EditText)findViewById(R.id.etFifthC);
        final Button bWelcome = (Button)findViewById(R.id.bWelcome);


        bWelcome.setOnClickListener(new View.OnClickListener(){ // LOGIN
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString().trim();
                String firstC = etFirstC.getText().toString().trim();
                String secondC = etSecondC.getText().toString().trim();
                String thridC = etThirdC.getText().toString().trim();
                String fourthC = etFourthC.getText().toString().trim();

                String fifthC =etFifthC.getText().toString().trim();
                final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

                final String QuestID = email.substring(0, email.length()-17);

                final String[] courses = new String[] {firstC, secondC, thridC, fourthC, fifthC};
                final List<CourseInfo> course_list = CourseInfo.getCourseListFromStringArray(courses);

                if(TextUtils.isEmpty(username)){
                    String message = getString(R.string.UserDisplayNameMissing);
                    etUsername.setHint(message);
                    etUsername.setHintTextColor(getResources().getColor(R.color.errorhint));
                    return;
                }


                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(QuestID)
                        .build();

                User.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    UserInfo newUser = new UserInfo(username, QuestID, course_list,  "Tell us about you" );
                                    FirebaseUserInfo.update_UserInfo(new UserPattern(newUser));

                                    Intent loginIntent = new Intent(SetUpProfile.this, MainActivity.class);
                                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    SetUpProfile.this.startActivity(loginIntent);
                                }else{
                                    return;
                                }
                            }
                        });

                /*final HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("username", username);
                dataMap.put("firstCourse", firstC);*/
                //mDatabase.child("name").setValue("Alice");

            }
        });
    }

}
