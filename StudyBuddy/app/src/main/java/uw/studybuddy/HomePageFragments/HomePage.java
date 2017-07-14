package uw.studybuddy.HomePageFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import uw.studybuddy.Events.EventsListRecycleViewFragment;
import uw.studybuddy.MainActivity;
import uw.studybuddy.R;
import uw.studybuddy.Resources.ResourcesListRecycleViewFragment;
import uw.studybuddy.Tutoring.TutorsListRecycleViewFragment;
import uw.studybuddy.UserProfile.UserInfo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomePage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static String clickedCourse = "";

    private Class currentListClass;
    private ToggleButton[] buttons;
    private TabLayout mCoursesTabLayout;
    private TabLayout.Tab[] coursesTabItems;

    public HomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        RadioGroup radioGroup = (RadioGroup)rootView.findViewById(R.id.homepage_togglegroup);
        ToggleButton eventsButton = (ToggleButton)rootView.findViewById(R.id.homepage_button_events);
        ToggleButton tutorsButton = (ToggleButton)rootView.findViewById(R.id.homepage_button_tutors);
        ToggleButton resourcesButton = (ToggleButton)rootView.findViewById(R.id.homepage_button_resources);

        // Set appropriate list for the categories selected
        buttons = new ToggleButton[]{eventsButton, tutorsButton, resourcesButton};
        Class[] classes = {EventsListRecycleViewFragment.class, TutorsListRecycleViewFragment.class,
                ResourcesListRecycleViewFragment.class};

        setCategoriesListeners(radioGroup, buttons, classes);


        // Create the courses tabs
        setupCoursesTabs(rootView);

        buttons[1].callOnClick();
        mCoursesTabLayout.getTabAt(coursesTabItems.length-1).select();
//        coursesTabItems[coursesTabItems.length - 1].select();
        return rootView;
    }

    private void setupCoursesTabs(View view) {
        mCoursesTabLayout = (TabLayout)view.findViewById(R.id.homepage_courses_tablayout);

        final List courses = UserInfo.getInstance().getCoursesList();
        int numCourses = courses.size();
        coursesTabItems = new TabLayout.Tab[numCourses+1];

        for (int i=0; i<numCourses; i++) {
            final String coursename = courses.get(i).toString();
            TabLayout.Tab tab = mCoursesTabLayout.newTab().setText(coursename);
            mCoursesTabLayout.addTab(tab);
            coursesTabItems[i] = tab;
        }
        TabLayout.Tab tab = mCoursesTabLayout.newTab().setIcon(R.mipmap.ic_clear);
        mCoursesTabLayout.addTab(tab);
        coursesTabItems[numCourses-1] = tab;

        mCoursesTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // The last icon clears the course selection
                if (tab.getPosition() == courses.size()) {
                    clickedCourse = "";
                } else {
                    clickedCourse = tab.getText().toString();
                }
                reloadListFragment();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }

    private void reloadListFragment() {
       setListFragmentTo(currentListClass);
    }

    private void setListFragmentTo(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.homepage_list, fragment).commit();
        currentListClass = fragmentClass;
    }

    private void setCategoriesListeners(final RadioGroup radioGroup, ToggleButton[] buttons, final Class[] classes){
        // Initialize the buttons
        for (int i=0; i<buttons.length; i++) {
            final int index = i;
            final ToggleButton tb = buttons[i];
            tb.setChecked(false);
            tb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tb.setChecked(!((ToggleButton)v).isChecked());
                    setListFragmentTo(classes[index]);
                }
            });
        }

        // set changes on radio group
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                // Update the buttons view
                for (int i=0; i<group.getChildCount(); i++) {
                    final ToggleButton view = (ToggleButton)group.getChildAt(i);
                    view.setChecked(view.getId() == checkedId);
                    if (view.getId() == checkedId) {
                        view.setBackgroundTintMode(PorterDuff.Mode.DARKEN);
                    } else {
                        view.setBackgroundTintMode(PorterDuff.Mode.LIGHTEN);
                    }
                }
                // Update the list view
                setListFragmentTo(classes[checkedId]);
            }
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
