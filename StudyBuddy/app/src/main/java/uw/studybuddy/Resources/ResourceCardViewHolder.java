package uw.studybuddy.Resources;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.kristijandraca.backgroundmaillibrary.BackgroundMail;

import uw.studybuddy.CustomCoursesSpinner;
import uw.studybuddy.R;

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

    public void setTitle(String title, final String link) {
        TextView titleView = (TextView)mView.findViewById(R.id.cardview_resource_title);
        titleView.setText(title);

//        titleView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
//                return false;
//            }
//        });
    }

    public void setUserNameWithQuestId(String questId, boolean isAnonymous){
        if (isAnonymous) {
            TextView header = (TextView)mView.findViewById(R.id.cardview_resource_by);
            TextView name = (TextView)mView.findViewById(R.id.cardview_resource_username);
            header.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
        } else {
            FirebaseUserInfo.setUserDisplayNameWithQuestId(questId, this);
        }
    }

    public void setDisplayName(String displayName) {
        TextView name = (TextView)mView.findViewById(R.id.cardview_resource_username);
        name.setText(displayName);
    }

    public void setLink(final String link){
//        TextView url = (TextView)mView.findViewById(R.id.cardview_resource_link);
//        url.setText(link);

        WebView webView = (WebView)mView.findViewById(R.id.cardview_resource_webview);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                showWebDialog(mView.getContext(), link);
                mView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                return true;
            }
        });
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }

    private void showWebDialog(Context context, String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_webview, null);
        WebView webView = (WebView)view.findViewById(R.id.webview);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);

        builder.setView(view);
//        builder.setNeutralButton("Done", null);
        builder.create();

        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        builder.show();
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
                    showEditDialog(mView.getContext(), resourceInfo);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDialog(mView.getContext(), resourceInfo);
                }
            });
        } else {
            flagButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            flagButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFlagDialog(mView.getContext(), resourceInfo);
                }
            });
        }
    }

    private void showEditDialog(Context context, final ResourceInfo resourceInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_new_resource, null);
        builder.setTitle("Edit Resource");
        builder.setView(view);

//         Setup the courses spinner
        final Spinner spinner = CustomCoursesSpinner.getSpinner(R.id.new_resource_courses_spinner, context, view);
        CustomCoursesSpinner.setSelectedText(resourceInfo.getCourse());

        // Get UI Components
        final EditText title = (EditText) view.findViewById(R.id.new_resource_title);
        final EditText link = (EditText) view.findViewById(R.id.new_resource_link);
        final CheckBox anonymous = (CheckBox) view.findViewById(R.id.anonymous_checkBox);

        // Set the current values
        title.setText(resourceInfo.getTitle());
        link.setText(resourceInfo.getLink());
        anonymous.setChecked(resourceInfo.isAnonymous());

        builder.setNeutralButton("cancel", null);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String course = spinner.getSelectedItem().toString();
                String newTitle = title.getText().toString();
                String newLink = link.getText().toString();
                boolean newAnonymous = anonymous.isChecked();

                resourceInfo.setCourse(course);
                resourceInfo.setTitle(newTitle);
                resourceInfo.setLink(newLink);
                resourceInfo.setAnonymous(newAnonymous);
                FirebaseResourceInfo.updateResource(resourceInfo);
            }
        });
        builder.show();
    }

    private void showDeleteDialog(Context context, final ResourceInfo resourceInfo) {
        String message = "Are you sure you want to delete this resource?";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage(message);

        builder.setNeutralButton("cancel", null);
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseResourceInfo.deleteResource(resourceInfo);
            }
        });

        builder.create();
        builder.show();
    }

    private void showFlagDialog(final Context context, final ResourceInfo resourceInfo) {
        /* Create the Intent */
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_flag_resource, null);
        builder.setView(view);
        builder.setTitle("Flag Resource As");

        final RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.flag_radiogroup);


        builder.setNeutralButton("cancel", null);
        builder.setPositiveButton("Flag", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)view.findViewById(selectedId);
                String title = "The following resource has been flagged.\n\n";
                String message = "ID: " + resourceInfo.getUuid().toString() + "\n";
                String by = "By: " + resourceInfo.getQuestId() + "\n";
                String reason = "Reason: " + radioButton.getText().toString() + "\n";

                // Send email to admin
                BackgroundMail bm = new BackgroundMail(context);
                String subject = "Flagged Resource";
                String email = "studybuddycs446@gmail.com";
                String password = "studybuddy123";
                bm.setGmailUserName(email);
                bm.setGmailPassword(password);
                bm.setMailTo(email);
                bm.setFormSubject(subject);
                bm.setFormBody(title + by + message + reason);
                bm.send();
            }
        });

        builder.create();
        builder.show();



    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
