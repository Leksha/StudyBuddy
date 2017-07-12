package uw.studybuddy.UserProfile;


import android.util.Log;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    public List<CourseInfo> getcourse(){
        List<CourseInfo> temp = new ArrayList<CourseInfo>(coursesList.values());
        Log.d(TAG, "getCourse = " + coursesList.values() + " list: " + temp );
        return  temp;
    }
    public void setcourse(List<CourseInfo> temp){
        //clear the courselist first
        this.coursesList.clear();
       for(int i =0; i < temp.size();i++){
           this.coursesList.put(Integer.toString(i), temp.get(i));
       }
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
        this.about_me = about_me;
    }


    //store the information on that
    public void get_user(DataSnapshot dataSnapshot,String key) {
            List<CourseInfo> courseList = new ArrayList<>();
            for (int i=0; i<7; i++) {
                CourseInfo course;
                course = dataSnapshot.child(key).child(FirebaseUserInfo.table_courses).child(Integer.toString(i)).getValue(CourseInfo.class);
                if (course != null) {
                    courseList.add(course);
                }
            }
            UserPattern temp = dataSnapshot.child(key).getValue(UserPattern.class);

            if(temp != null) {
                setabout_me(temp.getabout_me());
                setquest_id(temp.getquest_id());
                setdisplay_name(temp.getdisplay_name());
                if(courseList!=null) {
                    setcourse(courseList);
                }
        }
    }


}
