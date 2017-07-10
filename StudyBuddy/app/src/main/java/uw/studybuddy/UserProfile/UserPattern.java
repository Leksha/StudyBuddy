package uw.studybuddy.UserProfile;


import android.util.Log;

import com.endercrest.uwaterlooapi.courses.models.Course;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import uw.studybuddy.CourseInfo;
import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by Yuna on 17/7/7.
 */

public class UserPattern {

    private String TAG = "UserPattern";

    private String read;
    private String mQuestID;
    private String DisplayName;
    private String About_me;
    //coursename : coursename
    private Map<String, CourseInfo> coursesList = new HashMap<>();

    //quest id , displayname
    public Map<String, String> friend_list = new HashMap<>();


    public UserPattern(){
        mQuestID = "q79chen";
        About_me ="I am testing!";
    }

    //update the Userinfo
    public UserPattern(UserInfo User){
        read = "true";
        for( CourseInfo value : (List<CourseInfo>) User.getCoursesList())   {
            String temp = value.getSubject() + "" + value.getCatalogNumber();
            coursesList.put((String) temp, (CourseInfo) value);
        }
        About_me = User.getAboutMe();
        mQuestID = User.getQuestID();
        DisplayName = User.getDisplayName();
    }
    public List<CourseInfo> getCourse(){
        List<CourseInfo> temp = new ArrayList<CourseInfo>(coursesList.values());
        Log.d(TAG, "getCourse = " + coursesList.values() + " list: " + temp );
        return  temp;
    }
    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getmQuestID() {
        return mQuestID;
    }

    public void setmQuestID(String mQuestID) {
        this.mQuestID = mQuestID;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getAbout_me() {
        return About_me;
    }

    public void setAbout_me(String about_me) {
        About_me = about_me;
    }
}
