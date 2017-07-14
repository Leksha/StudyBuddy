package uw.studybuddy.Resources;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uw.studybuddy.R;
import uw.studybuddy.Tutoring.TutorInfo;
import uw.studybuddy.UserProfile.FirebaseUserInfo;

/**
 * Created by leksharamdenee on 2017-07-14.
 */

public class ResourceCardViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public ResourceCardViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }


    public void setCourse(String course){
        TextView cour = (TextView)mView.findViewById(R.id.cardview_resource_course);
        cour.setText(course);
    }

    public void setTitle(String title) {
        TextView titleView = (TextView)mView.findViewById(R.id.cardview_resource_title);
        titleView.setText(title);
    }

    public void setUserNameWithQuestId(String questId, boolean isAnonymous){
        if (isAnonymous) {
            TextView name = (TextView)mView.findViewById(R.id.cardview_resource_username);
            TextView header = (TextView)mView.findViewById(R.id.cardview_resource_title_header);
            name.setVisibility(View.GONE);
            header.setVisibility(View.GONE);
        } else {
            FirebaseUserInfo.setUserDisplayNameWithQuestId(questId, this);
        }
    }

    public void setDisplayName(String displayName) {
        TextView name = (TextView)mView.findViewById(R.id.cardview_resource_username);
        name.setText(displayName);
    }

    public void setLink(String link){
        TextView url = (TextView)mView.findViewById(R.id.cardview_resource_link);
        url.setText(link);
    }

    public void setButton(boolean isCreator, final ResourceInfo resourceInfo) {
        Button flagButton = (Button)mView.findViewById(R.id.cardview_resource_flag_button);
        Button editButton = (Button)mView.findViewById(R.id.cardview_resource_edit);
        Button deleteButton = (Button)mView.findViewById(R.id.cardview_resource_delete);

        if (isCreator) {
            flagButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showEditDialog(mView.getContext(), tutor);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showDeleteDialog(mView.getContext(), tutor);
                }
            });
        } else {
            flagButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            flagButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // showContactDialog(mView.getContext(), tutor);
                }
            });
        }
    }
}
