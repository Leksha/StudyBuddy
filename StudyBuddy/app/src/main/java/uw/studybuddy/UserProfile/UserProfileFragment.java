package uw.studybuddy.UserProfile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import uw.studybuddy.HomePage_Fragments.HomePage;
import uw.studybuddy.LoginAndRegistration.LoginActivity;
import uw.studybuddy.LoginAndRegistration.SetUpProfile;
import uw.studybuddy.MainActivity;
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

    private TextView mUserDisplayName;
    private TextView mUserName;
    private TextView mUserCourses;
    private TextView mUserAboutMe;

    private UserInfo user;

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


        mUserDisplayName = (TextView)rootView.findViewById(R.id.user_profile_display_name);
        mUserName = (TextView)rootView.findViewById(R.id.user_profile_name);
        mUserCourses = (TextView)rootView.findViewById(R.id.user_profile_courses);
        mUserAboutMe = (TextView)rootView.findViewById(R.id.user_profile_about_me);

        // For the purpose of the demo, we will create a user to display
        user = UserInfo.getInstance();

        // Update the user profile view with the right user info
        User = FirebaseAuth.getInstance().getCurrentUser();
        if(User!= null){
            mUserDisplayName.setText(User.getDisplayName());
            mUserName.setText(User.getDisplayName());
        }else{
            mUserDisplayName.setText("User.getDisplayName()");
            mUserName.setText("User.getDisplayName()");
            //To do switch to login
        }
        mUserCourses.setText(getCoursesString());
        mUserAboutMe.setText(user.getAboutMe());

        //setListeners();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (user == null) {
            user = UserInfo.getInstance();
        }
        User = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null){
            mUserDisplayName.setText(User.getDisplayName());
            mUserName.setText(User.getDisplayName());
        }else{
            mUserDisplayName.setText("User.getDisplayName()");
            mUserName.setText("User.getDisplayName()");
            //To do switch to login
        }
        mUserCourses.setText(getCoursesString());
        mUserAboutMe.setText(user.getAboutMe());
    }

    // Process the way user courses is displayed
    private String getCoursesString() {
        if (user == null) {
            return "";
        }
        String courses = "";
        String[] userCourses = user.getCourses();
        int length = userCourses.length;
        for (int i=0; i<length; i++) {
            courses += userCourses[i];
            if (i<length-1) {
                courses += ", ";
            }
        }
        return courses;
    }

    private void setListeners() {

        // Update display name
        mUserDisplayName.addTextChangedListener(new TextWatcher() {
            String currString;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currString = mUserDisplayName.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (currString !=null && currString != mUserDisplayName.getText().toString()) {
                    user.setDisplayName(mUserDisplayName.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mUserName.getText().toString() != user.getName()) {
                    user.setName(mUserName.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        mUserCourses.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mUserCourses.getText().toString() != user.getCourses().toString()) {
                    String[] newCourses = mUserCourses.getText().toString().split(", ");
                    user.setCourses(newCourses);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mUserAboutMe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mUserAboutMe.getText().toString() != user.getAboutMe()) {
                    user.setAboutMe(mUserAboutMe.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
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
