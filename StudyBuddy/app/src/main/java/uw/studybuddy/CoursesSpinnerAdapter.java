package uw.studybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by leksharamdenee on 2017-07-12.
 */

public class CoursesSpinnerAdapter extends BaseAdapter {
    Context context;
    String courses[];
    LayoutInflater inflater;

    public CoursesSpinnerAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater.from(context));
        List coursesList = UserInfo.getInstance().getCoursesList();
        courses = CourseInfo.getCoursesStringArrayFromList(coursesList).clone();
;    }

    @Override
    public int getCount() {
        return courses.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView)convertView.findViewById(R.id.custom_spinner_text_view);
        names.setText(courses[position]);
        return null;
    }
}
