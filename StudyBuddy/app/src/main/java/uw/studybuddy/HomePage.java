package uw.studybuddy;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import uw.studybuddy.UserProfile.UserInfo;
import uw.studybuddy.UserProfile.UserProfileActivity;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Data required for the app
    private static UserInfo user;

    private Button mSearchButton;
    private EditText mFindFriendEditText;
    private TextView mPrintFriendInfoTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_pane);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home Page");

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

        mSearchButton = (Button)findViewById(R.id.search_button);
        mFindFriendEditText = (EditText)findViewById(R.id.find_friend_editText);
        mPrintFriendInfoTextView = (TextView)findViewById(R.id.print_friend_info_textView);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String friendName = mFindFriendEditText.getText().toString();
                mPrintFriendInfoTextView.setText("Print " + friendName + "'s info here");
            }
        });

        mFindFriendEditText.addTextChangedListener(new TextWatcher() {
            String currString;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currString = mFindFriendEditText.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (currString !=null && currString != mFindFriendEditText.getText().toString()) {
                    mSearchButton.callOnClick();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        user = new UserInfo();

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
            mPrintFriendInfoTextView.setText("Create new event");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            System.out.println("Clicked on Home Page");
        } else if (id == R.id.nav_user_profile) {
            System.out.println("Clicked on User Profile");
            Intent userProfile = new Intent(HomePage.this, UserProfileActivity.class);
            HomePage.this.startActivity(userProfile);

        } else if (id == R.id.nav_friend_list) {

        } else if (id == R.id.nav_map) {

        }
// else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        else if (id == R.id.nav_log_out) {

        }

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
}

