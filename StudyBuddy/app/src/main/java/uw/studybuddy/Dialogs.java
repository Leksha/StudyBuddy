package uw.studybuddy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import uw.studybuddy.Resources.FirebaseResourceInfo;
import uw.studybuddy.Resources.ResourceInfo;
import uw.studybuddy.Tutoring.FirebaseTutor;
import uw.studybuddy.Tutoring.TutorInfo;
import uw.studybuddy.UserProfile.UserInfo;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by leksharamdenee on 2017-07-14.
 */

public class Dialogs {

    public static void showNewResourceDialog(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_new_resource, null);
        builder.setTitle("Add New Resource");
        builder.setView(view);

        // Get UI Components
        final EditText titleView = (EditText)view.findViewById(R.id.new_resource_title);
        final EditText urlView = (EditText)view.findViewById(R.id.new_resource_link);
        final Button pasteButton = (Button)view.findViewById(R.id.button_paste);
        final CheckBox anonymousCheckBox = (CheckBox)view.findViewById(R.id.anonymous_checkBox);

//         Setup the courses spinner
        final Spinner spinner = CustomCoursesSpinner.getSpinner(R.id.new_resource_courses_spinner, context, view);

        pasteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager myClipboard = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE);
                ClipData primaryClip = myClipboard.getPrimaryClip();
                ClipData.Item item = primaryClip.getItemAt(0);
                String copiedUrl = item.getText().toString();
                urlView.setText(copiedUrl);
            }
        });


        builder.setNeutralButton("cancel",null);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String course = spinner.getSelectedItem().toString();
                String title = titleView.getText().toString();
                String url = urlView.getText().toString();
                boolean isAnonymous = anonymousCheckBox.isChecked();

                ResourceInfo resource = new ResourceInfo(course, title, UserInfo.getInstance(), url, isAnonymous);
                FirebaseResourceInfo.addNewResource(resource);
            }
        });
        builder.show();
    }

    public static void showNewTutorDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_register_tutor, null);
        builder.setTitle("Register As Tutor");
        builder.setView(view);

        // Get UI Components
        final EditText price = (EditText)view.findViewById(R.id.tutor_price);
        final EditText phone = (EditText)view.findViewById(R.id.tutor_phone_number);
        final EditText email = (EditText)view.findViewById(R.id.tutor_email);

//         Setup the courses spinner
        final Spinner spinner = CustomCoursesSpinner.getSpinner(R.id.tutor_courses_spinner, context, view);

        builder.setNeutralButton("cancel",null);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String course = spinner.getSelectedItem().toString();
                String tutorPrice = price.getText().toString();
                String tutorPhone = phone.getText().toString();
                String tutorEmail = email.getText().toString();

                TutorInfo tutor = new TutorInfo(course, UserInfo.getInstance(), tutorPrice, tutorPhone, tutorEmail);
                FirebaseTutor.addNewTutor(tutor);
            }
        });
        builder.show();
    }


}
