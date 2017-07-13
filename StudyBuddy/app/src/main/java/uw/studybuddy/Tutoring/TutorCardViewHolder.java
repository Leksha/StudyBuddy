package uw.studybuddy.Tutoring;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import uw.studybuddy.CustomCoursesSpinner;
import uw.studybuddy.R;
import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by leksharamdenee on 2017-07-13.
 */

public class TutorCardViewHolder extends RecyclerView.ViewHolder {
    View mView;
    String button_edit = "EDIT";
    String button_contact = "CONTACT";

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

    public void setButton(boolean isTutor, final TutorInfo tutor) {
        Button contactButton = (Button)mView.findViewById(R.id.cardview_tutor_contact_button);
        Button editButton = (Button)mView.findViewById(R.id.cardview_tutor_edit);
        Button deleteButton = (Button)mView.findViewById(R.id.cardview_tutor_delete);

        if (isTutor) {
            contactButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditDialog(mView.getContext(), tutor);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDialog(mView.getContext(), tutor);
                }
            });
        } else {
            contactButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            contactButton.setText(button_contact);
            contactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showContactDialog(mView.getContext(), tutor);
                }
            });
        }
    }

    private void showEditDialog(Context context, final TutorInfo tutor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_register_tutor, null);
        builder.setTitle("Edit Tutor Information");
        builder.setView(view);

//         Setup the courses spinner
        final Spinner spinner = CustomCoursesSpinner.getSpinner(R.id.tutor_courses_spinner, context, view);
        CustomCoursesSpinner.setSelectedText(tutor.getCourse());

        // Get UI Components
        final EditText price = (EditText) view.findViewById(R.id.tutor_price);
        final EditText phone = (EditText) view.findViewById(R.id.tutor_phone_number);
        final EditText email = (EditText) view.findViewById(R.id.tutor_email);

        // Set the current values
        price.setText(tutor.getPrice());
        phone.setText(tutor.getPhoneNumber());
        email.setText(tutor.getEmail());

        builder.setNeutralButton("cancel", null);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String course = spinner.getSelectedItem().toString();
                String tutorPrice = price.getText().toString();
                String tutorPhone = phone.getText().toString();
                String tutorEmail = email.getText().toString();

                tutor.setCourse(course);
                tutor.setPrice(tutorPrice);
                tutor.setPhoneNumber(tutorPhone);
                tutor.setEmail(tutorEmail);
                FirebaseTutor.updateTutor(tutor);
            }
        });
        builder.show();
    }

    private void showContactDialog(Context context, TutorInfo tutor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_contact_tutor, null);
        builder.setTitle("Contact Tutor");
        builder.setView(view);

        // Get UI Components
        TextView phone = (TextView) view.findViewById(R.id.dialog_contact_phone);
        TextView email = (TextView) view.findViewById(R.id.dialog_contact_email);
        ImageButton phoneButton = (ImageButton) view.findViewById(R.id.dialog_contact_phone_button);
        ImageButton emailButton = (ImageButton) view.findViewById(R.id.dialog_contact_email_button);

        // Set the current values
        phone.setText(tutor.getPhoneNumber());
        email.setText(tutor.getEmail());

        builder.setNeutralButton("cancel", null);
        builder.show();
    }

    private void showDeleteDialog(Context context, final TutorInfo tutorInfo) {
        String message = "Are you sure you want to delete this message?";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage(message);

        builder.setNeutralButton("cancel", null);
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseTutor.deleteTutor(tutorInfo);
            }
        });

        builder.create();
        builder.show();
    }
}
