package uw.studybuddy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class StudyBuddyWelcome extends AppCompatActivity {

    private Handler handler;
    private long delay = 1500;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_study_buddy_welcome);

        Timer timer = new Timer();
        timer.schedule(task, delay);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Intent in = new Intent().setClass(StudyBuddyWelcome.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
        }
    };

}
