package uw.studybuddy.UserProfile.dummy;


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
        List<CourseInfo> course_list = User.getCoursesList();
        course = new HashMap<>();
        for(CourseInfo c : course_list){
            course.put(c.getSubject(), c.getCatalogNumber());
        }
        About_me = User.getAboutMe();
        mQuestID = User.getQuestID();
        DisplayName = User.getDisplayName();
    }
    public List<CourseInfo> getCourse(){
        List<CourseInfo> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : course.entrySet()) {
            CourseInfo c = new CourseInfo(entry.getKey(), entry.getValue());
            list.add(c);
        }
        return list;
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
