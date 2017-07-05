package uw.studybuddy;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.endercrest.uwaterlooapi.UWaterlooAPI;
import com.endercrest.uwaterlooapi.courses.models.Course;
import com.endercrest.uwaterlooapi.data.ApiRequest;

import java.net.URL;
import java.util.List;

/**
 * Created by leksharamdenee on 2017-06-27.
 * UW API KEY ed2c25922f87ba9a013af94a813856ff
 * https://github.com/uWaterloo/api-documentation#accessing-the-api
 */

public class UWAPI /*extends AsyncTask<Void, Void, String>*/ {

    private String apiKey = "ed2c25922f87ba9a013af94a813856ff";
    private UWaterlooAPI apiInstance;
    private String tag = "UWAPI";

    public UWAPI() {
        try {
            apiInstance = new UWaterlooAPI(apiKey);
        } catch (Exception e) {
            Log.d(tag, e.getLocalizedMessage());
        }
    }

    private UWaterlooAPI getInstance() {
        if (apiInstance == null) {
            try {
                apiInstance = new UWaterlooAPI(apiKey);
            } catch (Exception e) {
                Log.d(tag, e.getLocalizedMessage());
            }
        }
        return apiInstance;
    }

    public int getCourseBySubject(String course) {
        ApiRequest<List<Course>> req = getInstance().getCoursesAPI().getCourseBySubject(course);
        List<Course> courses = req.getData();
        return course.length();
    }


}
