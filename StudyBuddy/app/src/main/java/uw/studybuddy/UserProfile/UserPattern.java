package uw.studybuddy.UserProfile;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uw.studybuddy.CourseInfo;

/**
 * Created by Yuna on 17/7/7.
 */

public class UserPattern {

    private String TAG = "UserPattern";

    private String read;
    private String quest_id;
    private String display_name;
    private String about_me;
    //coursename : coursename
    private Map<String, CourseInfo> coursesList = new HashMap<>();

    //quest id , displayname
    public Map<String, String> friend_list = new HashMap<>();


    public UserPattern(){
        quest_id = "";
        about_me ="";
    }

    //update the Userinfo
    public UserPattern(UserInfo User){
        read = "true";
        for( CourseInfo value : (List<CourseInfo>) User.getCoursesList())   {
            String temp = value.getSubject() + "" + value.getCatalogNumber();
            coursesList.put((String) temp, (CourseInfo) value);
        }
        about_me = User.getAboutMe();
        quest_id = User.getQuestID();
        display_name = User.getDisplayName();
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

    public String getquest_id() {
        return quest_id;
    }

    public void setquest_id(String mQuestID) {
        this.quest_id = mQuestID;
    }

    public String getdisplay_name() {
        return display_name;
    }

    public void setdisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getabout_me() {
        return about_me;
    }

    public void setabout_me(String about_me) {
        about_me = about_me;
    }
}
