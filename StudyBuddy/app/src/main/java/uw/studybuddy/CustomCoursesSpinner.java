package uw.studybuddy;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leksharamdenee on 2017-07-13.
 */

public class CustomCoursesSpinner {
    private static List<String> list;
    private static Spinner spinner;


    public static Spinner getSpinner(int resourceId, Context context, View view){
        spinner = (Spinner)view.findViewById(resourceId);
        addItemsOnCourseSpinner(spinner, context);
        addListenerOnSpinnerItemSelection(spinner);
        return spinner;
    }


    private static void addItemsOnCourseSpinner(Spinner spinner, Context context) {
        List coursesList = uw.studybuddy.UserProfile.UserInfo.getInstance().getCoursesList();
        list = new ArrayList<>(CourseInfo.getCourseStringsListFromList(coursesList));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public static void addListenerOnSpinnerItemSelection(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public static void setSelectedText(String course) {
        spinner.setSelection(list.indexOf(course));
    }
}
