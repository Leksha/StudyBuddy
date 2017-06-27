package uw.studybuddy.Events;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uw.studybuddy.R;

/**
 * Created by leksharamdenee on 2017-06-27.
 * Uses the event_cardview.xml file
 */

public class EventCardViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public EventCardViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setDescription(String description){
        TextView desc = (TextView)mView.findViewById(R.id.tvDescription);
        desc.setText(description);
    }
    public void setCourse(String course){
        TextView cour = (TextView)mView.findViewById(R.id.tvCourse);
        cour.setText(course);
    }
    public void setLocation(String location){
        TextView loc = (TextView)mView.findViewById(R.id.tvLocation);
        loc.setText(location);
    }
    public void setSubject(String subject){
        TextView subj = (TextView)mView.findViewById(R.id.tvSubject);
        subj.setText(subject);
    }
}
