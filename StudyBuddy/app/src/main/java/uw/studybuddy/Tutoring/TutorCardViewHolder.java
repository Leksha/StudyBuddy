package uw.studybuddy.Tutoring;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uw.studybuddy.R;

/**
 * Created by leksharamdenee on 2017-07-13.
 */

public class TutorCardViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public TutorCardViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setCourse(String course){
        TextView cour = (TextView)mView.findViewById(R.id.cardview_tutor_course);
        cour.setText(course);
    }

    public void setTutorName(String name){
        TextView tutorName = (TextView)mView.findViewById(R.id.cardview_tutor_name);
        tutorName.setText(name);
    }

    public void setPrice(String price){
        TextView tutorPrice = (TextView)mView.findViewById(R.id.cardview_tutor_price);
        tutorPrice.setText(price + "/hour");
    }
}
