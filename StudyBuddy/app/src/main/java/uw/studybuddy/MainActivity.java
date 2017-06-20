package uw.studybuddy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uw.studybuddy.HomePage_Fragments.DisplayCourses;
import uw.studybuddy.HomePage_Fragments.FindFriends;
import uw.studybuddy.HomePage_Fragments.HomePage;
import uw.studybuddy.UserProfile.UserInfo;
import uw.studybuddy.UserProfile.UserProfileActivity;

public class MainActivity extends AppCompatActivity
        implements HomePage.OnFragmentInteractionListener,
        FindFriends.OnFragmentInteractionListener,
        DisplayCourses.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    // Data required for the app
    private UserInfo user;

    private RecyclerView  mEventList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home Page");

        // Setup the fragment to be displayed
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = FindFriends.class;
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");

        mEventList = (RecyclerView)findViewById(R.id.event_list);
        mEventList.setHasFixedSize(true);
        mEventList.setLayoutManager(new LinearLayoutManager(this));

        user = new UserInfo();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Event, EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(
                Event.class,
                R.layout.event_card,
                EventViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Event model, int position) {
                viewHolder.setCourse(model.getCourse());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setSubject(model.getSubject());
            }
        };
        mEventList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public EventViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setDescription(String description){
            TextView desc = (TextView)mView.findViewById(R.id.tvDescription);
            desc.setText(description);
        }
        public void setCourse(String course){
            TextView cour = (TextView)mView.findViewById(R.id.tvCourse);
            cour.setText(course);
        }
        public void setLocation(String location){
            TextView loc = (TextView)mView.findViewById(R.id.tvLocation);
            loc.setText(location);
        }
        public void setSubject(String subject){
            TextView subj = (TextView)mView.findViewById(R.id.tvSubject);
            subj.setText(subject);
        }
    }

    // Update the view if any changes made to user data
    @Override
    protected void onResume() {
        super.onResume();
        if (user == null) {
            user = UserInfo.getInstance();
        }
        updateNavigationDrawerUserInfo();
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
            // Handle the camera action
            System.out.println("Clicked on Home Page");
            fragmentClass = HomePage.class;
        } else if (id == R.id.nav_user_profile) {
            System.out.println("Clicked on User Profile");
            Intent userProfile = new Intent(MainActivity.this, UserProfileActivity.class);
            MainActivity.this.startActivity(userProfile);

        } else if (id == R.id.nav_friend_list) {
            fragmentClass = FindFriends.class;

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

    public void GotoNewEvent(MenuItem item) {
        Intent intent = new Intent(this, EventCreation.class);
        startActivity(intent);
    }

    private void updateNavigationDrawerUserInfo() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView userImage = (ImageView)headerView.findViewById(R.id.navigation_draw_user_image);
        TextView userName = (TextView)headerView.findViewById(R.id.navigation_draw_user_name);

        userName.setText(user.getName().toString());
    }

    public void LogOut(MenuItem item) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void GoToFriendList(MenuItem item) {
        startActivity(new Intent(this, FriendList.class));
    }
            
    // OnFragmentInteractionListeners
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

