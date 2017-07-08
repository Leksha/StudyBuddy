package uw.studybuddy.UserProfile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import uw.studybuddy.CourseInfo;
import uw.studybuddy.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    private EditText mUserDisplayName;
    private EditText mUserName;
    private EditText mUserAboutMe;
    private UserInfo user;

    private Button mUserDisplayNameEditButton;
    private Button mUserNameEditButton;
    private Button mUserAboutMeEditButton;
    private Button mAddCourseButton;

    private LinearLayout mUserCoursesLayout;
    private List<Button> mCoursesButtons;
    private List<CourseInfo> mCoursesList;
  
    private FirebaseAuth.AuthStateListener mAuthListener;
    private  FirebaseAuth mAuth;
    private FirebaseUser User;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String TAG = "user_profile_fragment";
    private OnFragmentInteractionListener mListener;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                User = firebaseAuth.getCurrentUser();
                if(User != null){
                    //user log in state is fine
                    //do nothing for now
                    //mDatabase = FirebaseDatabase.getInstance().getReference();
                }else{
                    //user has already log out
                    //do the log in again
                    //dont know how to swicth back to log out
                    //To do
                    return;
                }
            }
        };


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);


        mUserDisplayName = (EditText)rootView.findViewById(R.id.user_profile_display_name);
        mUserName = (EditText)rootView.findViewById(R.id.user_profile_name);
        mUserCoursesLayout = (LinearLayout)rootView.findViewById(R.id.user_profile_courses_linear_layout);
        mUserAboutMe = (EditText)rootView.findViewById(R.id.user_profile_about_me);

        mUserDisplayNameEditButton = (Button)rootView.findViewById(R.id.user_display_name_edit_button);
        mUserNameEditButton = (Button)rootView.findViewById(R.id.user_name_edit_button);
        mUserAboutMeEditButton = (Button)rootView.findViewById(R.id.user_about_me_edit_button);

        mAddCourseButton = (Button)rootView.findViewById(R.id.add_course_button);

        // For the purpose of the demo, we will create a user to display
        user = UserInfo.getInstance();

        // Update the user profile view with the right user info
        mUserDisplayName.setText(user.getDisplayName());
        mUserName.setText(user.getName());
        mUserAboutMe.setText(user.getAboutMe());

        // Add the courses buttons
        mCoursesList = UserInfo.getCourses();
        int numCourses = mCoursesList.size();
        mCoursesButtons = new ArrayList<>();
        mUserCoursesLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        int diameter = 100;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(diameter, diameter-10);
        params.setMargins(2,2,2,2);

        for (int i=0; i<numCourses; i++) {
            final Button button = createButton(mCoursesList.get(i));
            mCoursesButtons.add(button);
            final int  index = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(index, button, false);
                }
            });
            mUserCoursesLayout.addView(button,params);
        }

        setListeners();

        // Should update current user profile with names
        User = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null){
            mUserDisplayName.setText(User.getDisplayName());
            mUserName.setText(User.getDisplayName());
        }else{
            mUserDisplayName.setText("User.getDisplayName()");
            mUserName.setText("User.getDisplayName()");
            //To do switch to login
        }
        mUserAboutMe.setText(user.getAboutMe());

        return rootView;
    }

    private Button createButton(CourseInfo course) {
        Button button = new Button(this.getContext());
        button.setBackgroundResource(R.drawable.rounded_corners_button);
        button.setText(course.toString());
        button.setTextSize(11);
        button.setClickable(true);
        button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        return button;
    }

    // Using the same dialog to add and edit courses
    private void showDialog(final int index, Button button, final boolean isAdd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.edit_course_name_dialog, null);
        final EditText edit_dialog_course_subject = (EditText) view.findViewById(R.id.edit_course_subject);
        final EditText edit_dialog_course_number = (EditText) view.findViewById(R.id.edit_course_number);
        builder.setView(view);

        final Button btn = button;
        final int i = index;

        if (isAdd) {
            builder.setTitle("Add New Course");
        } else {
            edit_dialog_course_subject.setText(mCoursesList.get(index).getSubject());
            edit_dialog_course_number.setText(mCoursesList.get(index).getCatalogNumber());

            builder.setTitle("Edit Course");
            builder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    user.deleteCourse(i);
                    reloadFragment();
                }
            });
        }

        builder.setNeutralButton("cancel",null);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sub = edit_dialog_course_subject.getText().toString();
                String num = edit_dialog_course_number.getText().toString();
                String text = sub + " " + num;
                if (isAdd) {
                    user.addCourse(sub, num);
                } else {
                    user.updateCourseInfo(i, sub, num);
//                    btn.setText(text);
                }
                reloadFragment();

            }
        });
        builder.show();
    }

    // Refresh fragment when user deletes a course
    private void reloadFragment() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (user == null) {
            user = UserInfo.getInstance();
        }

        mUserDisplayName.setText(user.getDisplayName());
        mUserName.setText(user.getName());

        User = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null){
            mUserDisplayName.setText(User.getDisplayName());
            mUserName.setText(User.getDisplayName());
        }else{
            mUserDisplayName.setText("User.getDisplayName()");
            mUserName.setText("User.getDisplayName()");
            //To do switch to login
        }

        mUserAboutMe.setText(user.getAboutMe());
        int len = mCoursesList.size();
        for (int i=0; i<len; i++) {
            mCoursesButtons.get(i).setText(mCoursesList.get(i).toString());
        }
    }


    private void setListeners() {
        Button[] editButtons = {mUserDisplayNameEditButton, mUserNameEditButton, mUserAboutMeEditButton};
        EditText[] userInfoEditTexts = {mUserDisplayName, mUserName, mUserAboutMe};

        // Only editable when edit button is clicked
        for (int i=0; i<editButtons.length; i++) {
            final Button button = editButtons[i];
            final EditText currEditText = userInfoEditTexts[i];
            currEditText.setFocusable(false);

            final int index = i;
            editButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currEditText.isFocusable()) {
                        currEditText.setFocusable(false);
                        button.setBackgroundResource(R.mipmap.edit_icon);

                        // Only update UserInfo class if the text is changed
                        if (getTextFromUserProfile(index) != currEditText.getText().toString()) {
                            setTextForUserProfile(index, currEditText.getText().toString());
                        }
                    } else {
                        currEditText.setFocusableInTouchMode(true);
                        button.setBackgroundResource(R.mipmap.done_icon);
                    }
                }
            });
        }
        mAddCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0, null, true);
            }
        });


    }



    private String getTextFromUserProfile(int index) {
        String answer;
        switch (index) {
            case 0: answer = user.getDisplayName(); break;
            case 1: answer = user.getName(); break;
            case 2: answer = user.getAboutMe(); break;
            default: answer = "Error"; break;
        }
        Log.d(TAG, answer);
        return answer;
    }

    private void setTextForUserProfile(int index, String newText) {
        switch (index) {
            case 0: user.setDisplayName(newText); break;
            case 1: user.setName(newText); break;
            case 2: user.setAboutMe(newText); break;
            default: break;
        }
        Log.d(TAG, newText);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
