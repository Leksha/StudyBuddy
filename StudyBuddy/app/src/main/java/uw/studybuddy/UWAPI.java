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

    private static String apiKey = "ed2c25922f87ba9a013af94a813856ff";
    private static UWaterlooAPI apiInstance;
    private static String tag = "UWAPI";

    public UWAPI() {
        try {
            apiInstance = new UWaterlooAPI(apiKey);
        } catch (Exception e) {
            Log.d(tag, e.getLocalizedMessage());
        }
    }

    private static UWaterlooAPI getInstance() {
        if (apiInstance == null) {
            try {
                apiInstance = new UWaterlooAPI(apiKey);
            } catch (Exception e) {
                Log.d(tag, e.getLocalizedMessage());
            }
        }
        return apiInstance;
    }

    public static int getCourseBySubject(String course) {
        // To test call getCourseBySubject("CS");

        ApiRequest<List<Course>> req = getInstance().getCoursesAPI().getCourseBySubject(course);
        List<Course> courses = req.getData();
        return course.length();
    }


}
