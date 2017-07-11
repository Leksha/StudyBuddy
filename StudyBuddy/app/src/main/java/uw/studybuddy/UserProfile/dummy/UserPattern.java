package uw.studybuddy.UserProfile.dummy;


import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by Yuna on 17/7/7.
 */

public class UserPattern {

    private String read;
    private String mQuestID;
    private String DisplayName;
    private String About_me;
    //coursename : coursename
    public Map<String, String> course = new HashMap<>();

    //quest id , displayname
    public Map<String, String> friend_list = new HashMap<>();


    public UserPattern(){
        mQuestID = "q79chen";
        About_me ="I am testing!";
    }

    //update the Userinfo
    public UserPattern(UserInfo User){
        read = "true";
        String[] course_list = User.getCourses().clone();
        for(String  value : course_list){
            course.put(value, value);
        }
        About_me = User.getAboutMe();
        mQuestID = User.getName();
        DisplayName = User.getDisplayName();
    }
    public String[] getCourse(){
        String[] temp = new String[6];
        if(course != null) {
            course.keySet().toArray(temp);
        }
        return temp;
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
