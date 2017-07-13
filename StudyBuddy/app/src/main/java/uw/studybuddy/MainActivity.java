package uw.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import uw.studybuddy.Events.EventCreation;
import uw.studybuddy.Events.EventsListRecycleViewFragment;
import uw.studybuddy.HomePageFragments.DisplayCourses;
import uw.studybuddy.HomePageFragments.FindFriends;
import uw.studybuddy.HomePageFragments.HomePage;
import uw.studybuddy.LoginAndRegistration.LoginActivity;
import uw.studybuddy.Tutoring.TutorInfo;
import uw.studybuddy.UserProfile.FirebaseUserInfo;
import uw.studybuddy.UserProfile.FriendListFragment;
import uw.studybuddy.UserProfile.UserInfo;
import uw.studybuddy.UserProfile.UserProfileFragment;

import uw.studybuddy.UserProfile.UserPattern;



public class MainActivity extends AppCompatActivity
        implements HomePage.OnFragmentInteractionListener,
        FindFriends.OnFragmentInteractionListener,
        DisplayCourses.OnFragmentInteractionListener,
        EventsListRecycleViewFragment.OnFragmentInteractionListener,
        FriendListFragment.OnListFragmentInteractionListener,
        UserProfileFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private UserInfo user;
    public static Activity fa;

    private boolean firstTimeHomePageInitialize = false;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home Page");

        fa = this;

        // Setup UserInfo class
        setupUserProfile();
      
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    // Update the view if any changes made to user data
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (user == null) {
//            user = UserInfo.getInstance();
//        }
//        updateNavigationDrawerUserInfo();
//    }

    private void setupHomepage(){
        // Setup the fragment to be displayed
        Fragment fragment = null;
        Class fragmentClass = HomePage.class;
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_pane, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.action_new_event) {
        }
        if (id == R.id.action_new_tutor) {
            Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG);
            showNewTutorDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
//            recreate();
            fragmentClass = HomePage.class;
        } else if (id == R.id.nav_user_profile) {
            System.out.println("Clicked on User Profile");
            fragmentClass = UserProfileFragment.class;
        } else if (id == R.id.nav_friend_list) {
            fragmentClass = FriendListFragment.class;

        }
//        else if (id == R.id.nav_map) {
//
//        }
// else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        else if (id == R.id.nav_log_out) {

        }

        // Display the new fragment
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Close the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // User setup-related code
    private void setupUserProfile() {
        // Updating the Display name on the user
        FirebaseUser Users = FirebaseUserInfo.getCurrentFirebaseUser();
        UserProfileChangeRequest profileupdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(FirebaseUserInfo.get_QuestId())
                .build();
        Users.updateProfile(profileupdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                }
            }
        });

        // Create UserInfo class

        // Update User Profile when it is changed
        setupUserProfileListener();
        FirebaseUserInfo.set_UserRead("false");
    }

    // Updates UserInfo class when user data is changed
    private void setupUserProfileListener() {
        FirebaseUserInfo.getCurrentUserRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateUserInfoClass(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }


    private void updateUserInfoClass(DataSnapshot dataSnapshot) {
        UserPattern temp = dataSnapshot.getValue(UserPattern.class);

        // Process courses table from database
        List<CourseInfo> courseList = new ArrayList<>();
        for (int i=0; i<7; i++) {
            CourseInfo course;
            course = dataSnapshot.child(FirebaseUserInfo.table_courses).child(Integer.toString(i)).getValue(CourseInfo.class);
            if (course != null) {
                courseList.add(course);
            }
        }
        if(temp != null){
            user = new UserInfo(temp.getdisplay_name(),temp.getquest_id(),courseList ,temp.getabout_me());
            updateNavigationDrawerUserInfo();

            // Only force the main activity to be initialized to home page once
            if (!firstTimeHomePageInitialize) {
                firstTimeHomePageInitialize = true;
                setMainActivityToHomePage();
            }
        }
    }

    private void updateNavigationDrawerUserInfo() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView userImage = (ImageView)headerView.findViewById(R.id.navigation_draw_user_image);
        TextView userName = (TextView)headerView.findViewById(R.id.navigation_draw_user_name);

        if(user != null && user.getDisplayName() != null) {
            userName.setText(user.getDisplayName().toString());
        }else{
            userName.setText("Nooooo!");
        }
    }

    private void setMainActivityToHomePage() {
        if (user != null) {
            Fragment fragment = null;
            Class fragmentClass = HomePage.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
    }


    // OnFragmentInteractionListeners
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onListFragmentInteraction(Uri uri) {

    }

    public void GotoNewEvent(MenuItem item) {
        Intent intent = new Intent(this, EventCreation.class);
        startActivity(intent);
    }


    public void LogOut(MenuItem item) {
        FirebaseAuth fAuth = FirebaseInstance.getFirebaseAuthInstance();
        fAuth.signOut();

        Intent newIntent = new Intent(this, LoginActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(newIntent);
    }

    // Called when clicking on New Tutor on Action Bar
    public void showNewTutorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_register_tutor, null);
        builder.setTitle("Register As Tutor");
        builder.setView(view);

        // Get UI Components
        final EditText price = (EditText)view.findViewById(R.id.tutor_price);
        final EditText phone = (EditText)view.findViewById(R.id.tutor_phone_number);
        final EditText email = (EditText)view.findViewById(R.id.tutor_email);

//         Setup the courses spinner
        final Spinner spinner = (Spinner)view.findViewById(R.id.tutor_courses_spinner);

        addItemsOnCourseSpinner(spinner);
        addListenerOnSpinnerItemSelection(spinner);

        builder.setNeutralButton("cancel",null);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String course = spinner.getSelectedItem().toString();
                String tutorPrice = price.getText().toString();
                String tutorPhone = phone.getText().toString();
                String tutorEmail = email.getText().toString();
                TutorInfo tutor = new TutorInfo(course, UserInfo.getInstance(), tutorPrice, tutorPhone, tutorEmail);
                String message = "New tutor: " + course + " " + tutorPrice + " " + tutorPhone + " " + tutorEmail;
                Toast.makeText(context, message, Toast.LENGTH_LONG);
            }
        });
        builder.show();
    }

    private void addItemsOnCourseSpinner(Spinner spinner) {
        List coursesList = uw.studybuddy.UserProfile.UserInfo.getInstance().getCoursesList();
        List<String> list = CourseInfo.getCourseStringsListFromList(coursesList);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}

