package uw.studybuddy.CourseChat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uw.studybuddy.R;
import uw.studybuddy.UserProfile.UserInfo;
import uw.studybuddy.UserProfile.UserPattern;

public class CoursesChatActivity extends AppCompatActivity {

    private ListView CourseList;
    private ArrayList<String> CourseArrayList;
    private ArrayAdapter<String> CourseAdapter;

    private String username;

    DatabaseReference mDatabaseCourse;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    UserInfo User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_chat);

        CourseList = (ListView) findViewById(R.id.CourseList);

        CourseArrayList = new ArrayList<String>();
        CourseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CourseArrayList);
        mDatabaseCourse = FirebaseDatabase.getInstance().getReference().child("CourseChat");

        CourseList.setAdapter(CourseAdapter);
        Map<String, Object> map = new HashMap<String, Object>();
        final List<String> courses = UserPattern.list_courseInfo_toString(User.getInstance().getCoursesList());
        /*for(String value : courses){
            map.put(value, "");
        }*/
        //mDatabaseCourse.updateChildren(map);
        //map.put(User.getInstance().getCoursesList() + ": " + model.getTitle(), "");
        //mDatabaseChat.updateChildren(map);


        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        username = User.getInstance().getDisplayName();
        //requestEvent();

        //retrieve data
        mDatabaseCourse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Iterator iterator = dataSnapshot.getChildren().iterator();

                Set<String> set = new HashSet<String>();
                for(String val : courses){
                    set.add(val);
                }

                /*while (iterator.hasNext()){
                    // get names of all the events one by one
                    set.add((String) ((DataSnapshot)iterator.next()).getKey());

                }*/
                CourseArrayList.clear();
                CourseArrayList.addAll(set);

                CourseAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        CourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CoursesChatActivity.this, CurrentCourseChat.class);
                intent.putExtra("eventname", ((TextView)view).getText().toString());
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
