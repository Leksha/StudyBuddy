package uw.studybuddy.LoginAndRegistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uw.studybuddy.R;

public class TermsAndConditionsActivity extends AppCompatActivity {

    private TextView TermsAndConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        TextView TAC = (TextView) findViewById(R.id.TermsContent);
        TAC.setText("Condition and Terms\n" +
                "\n" +
                "1. You must be at least a Student/Professor/TA in University of Waterloo\n" +
                "2. You may not post violent, nude, partially nude, discriminatory, unlawful, infringing, hateful, pornographic or sexually suggestive photos or other content via the Service.\n" +
                "3. You are responsible for any activity that occurs through your account and you agree you will not sell, transfer, license or assign your account, followers, username, or any account rights. With the exception of people or businesses that are expressly authorized to create accounts on behalf of their employers or clients, StudyBuddy prohibits the creation of and you agree that you will not create an account for anyone other than yourself. You also represent that all information you provide or provided to StudyBuddy upon registration and at all other times will be true, accurate, current and complete and you agree to update your information as necessary to maintain its truth and accuracy.\n" +
                "4. You agree that you will not solicit, collect or use the login credentials of other StudyBuddy users.\n" +
                "5. You are responsible for keeping your password secret and secure.\n" +
                "6. You must not defame, stalk, bully, abuse, harass, threaten, impersonate or intimidate people or entities and you must not post private or confidential information via the Service, including, without limitation, your or any other person's credit card information, social security or alternate national identity numbers, non-public phone numbers or non-public email addresses.\n" +
                "7. You may not use the Service for any illegal or unauthorized purpose. You agree to comply with all laws, rules and regulations (for example, federal, state, local and provincial) applicable to your use of the Service and your Content (defined below), including but not limited to, copyright laws.\n" +
                "8. You are solely responsible for your conduct and any data, text, files, information, usernames, images, graphics, photos, profiles, audio and video clips, sounds, musical works, works of authorship, applications, links and other content or materials (collectively, \"Content\") that you submit, post or display on or via the Service.\n" +
                "9. You must not change, modify, adapt or alter the Service or change, modify or alter another website so as to falsely imply that it is associated with the Service or StudyBuddy.\n" +
                "10. You must not create or submit unwanted email, comments, likes or other forms of commercial or harassing communications (a/k/a \"spam\") to any StudyBuddy users.\n" +
                "11. You must not interfere or disrupt the Service or servers or networks connected to the Service, including by transmitting any worms, viruses, spyware, malware or any other code of a destructive or disruptive nature. \n" +
                "12. You must not attempt to restrict another user from using or enjoying the Service and you must not encourage or facilitate violations of these Terms of Use or any other StudyBuddy terms.\n" +
                "13. Violation of these Terms of Use may, in StudyBuddy's sole discretion, result in termination of your StudyBuddy account. You understand and agree that StudyBuddy cannot and will not be responsible for the Content posted on the Service and you use the Service at your own risk. If you violate the letter or spirit of these Terms of Use, or otherwise create risk or possible legal exposure for StudyBuddy, we can stop providing all or part of the Service to you.\n" +
                "\n" +
                "Condition:\n" +
                "1. We reserve the right to modify or terminate the Service or your access to the Service for any reason, without notice, at any time, and without liability to you. \n" +
                "2. Upon termination, all licenses and other rights granted to you in these Terms of Use will immediately cease.\n" +
                "3. We reserve the right, in our sole discretion, to change these Terms of Use (\"Updated Terms\") from time to time. Unless we make a change for legal or administrative reasons, we will provide reasonable advance notice before the Updated Terms become effective. You agree that we may notify you of the Updated Terms by posting them on the Service, and that your use of the Service after the effective date of the Updated Terms (or engaging in such other conduct as we may reasonably specify) constitutes your agreement to the Updated Terms. Therefore, you should review these Terms of Use and any Updated Terms before using the Service. The Updated Terms will be effective as of the time of posting, or such later date as may be specified in the Updated Terms, and will apply to your use of the Service from that point forward. These Terms of Use will govern any disputes arising before the effective date of the Updated Terms.\n" +
                "4. We reserve the right to refuse access to the Service to anyone for any reason at any time.\n" +
                "5. We reserve the right to force forfeiture of any username for any reason.\n" +
                "6. We may, but have no obligation to, remove, edit, block, and/or monitor Content or accounts containing Content that we determine in our sole discretion violates these Terms of Use.\n" +
                "7. You are solely responsible for your interaction with other users of the Service, whether online or offline. You agree that StudyBuddy is not responsible or liable for the conduct of any user. StudyBuddy reserves the right, but has no obligation, to monitor or become involved in disputes between you and other users. Exercise common sense and your best judgment when interacting with others, including when you submit or post Content or any personal or other information.\n" +
                "8. You agree that you are responsible for all data charges you incur through use of the Service.\n" +
                "9. We prohibit crawling, scraping, caching or otherwise accessing any content on the Service via automated means, including but not limited to, user profiles and photos \n");
    }

    public void Register(View view) {
        startActivity(new Intent(TermsAndConditionsActivity.this, RegisterActivity.class));
    }
}
