package uw.studybuddy.Events;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kristijandraca.backgroundmaillibrary.BackgroundMail;

import java.util.HashMap;
import java.util.Map;

import uw.studybuddy.HomePageFragments.DisplayCourses;
import uw.studybuddy.HomePageFragments.HomePage;
import uw.studybuddy.MainActivity;
import uw.studybuddy.R;
import uw.studybuddy.UserProfile.FirebaseUserInfo;
import uw.studybuddy.UserProfile.UserInfo;
import uw.studybuddy.UserProfile.UserPattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsListRecycleViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsListRecycleViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsListRecycleViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseCourse;
    private Query mQueryCourse;
    private UserInfo user_temp;

    private RecyclerView rv;
    private String TAG = "EventsListRVFragment";

    private DatabaseReference mJoinEvent;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    UserInfo User;

    private boolean isJoinEvent = false;

    private OnFragmentInteractionListener mListener;
    private FirebaseRecyclerAdapter<EventInfo, EventCardViewHolder> fbRecyclerAdapter;

    public EventsListRecycleViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsListRecycleViewFragment.
     */
    // TODO: Rename and change types and number of parameters
        public static EventsListRecycleViewFragment newInstance(String param1, String param2) {
        EventsListRecycleViewFragment fragment = new EventsListRecycleViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        Setup_UsertableListener();

        user_temp = UserInfo.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        mJoinEvent = FirebaseDatabase.getInstance().getReference().child("Participants");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabaseCourse = FirebaseDatabase.getInstance().getReference().child("Event");
        if(HomePage.clickedCourse.isEmpty()){
            mQueryCourse = mDatabaseCourse;
        } else {
            mQueryCourse = mDatabaseCourse.orderByChild("course").equalTo(HomePage.clickedCourse);
        }

        //String
        mDatabase.keepSynced(true);
        mJoinEvent.keepSynced(true);

        fbRecyclerAdapter = new FirebaseRecyclerAdapter<EventInfo, EventCardViewHolder>(
                EventInfo.class,
                R.layout.event_cardview,
                EventCardViewHolder.class,
                mQueryCourse
        ) {
            @Override
            protected void populateViewHolder(final EventCardViewHolder viewHolder, final EventInfo model, int position) {
                final String eventKey = getRef(position).getKey();

                viewHolder.setCourse(model.getCourse());
                //viewHolder.setDescription(model.getDescription());
                //viewHolder.setImage(getContext(), User.getInstance().getmImage());
                viewHolder.setDate("Date: "+model.getDate()+" | Time: "+ model.getTime());
                UserPattern Userholder = new UserPattern();

                Userholder.get_user(user_temp.getmUserTable_DS(), model.getQuestId());
                String Url_temp ;
                if(user_temp.getmUserTable_DS() == null || model.getQuestId() == null){
                    Url_temp = "android.resource://uw.studybuddy/mipmap/ic_default_user";
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                }else{
                    Url_temp = Userholder.getImage();
                }

                viewHolder.setImage(getContext(), Uri.parse(Url_temp));

                viewHolder.setTitle(model.getTitle());
                viewHolder.setJoinEvent(eventKey, model.getUid());
//                 viewHolder.setSubject(model.getSubject());
                Log.d(TAG, "populateViewHolder");

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent clickedEvent = new Intent(getActivity(), EventDescription.class);
                        clickedEvent.putExtra("event_id", eventKey);
                        startActivity(clickedEvent);
                    }
                });

                viewHolder.BjoinEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(viewHolder.BjoinEvent.getText() == "Review"){
                            Intent clickedEvent = new Intent(getActivity(), EventDescription.class);
                            clickedEvent.putExtra("event_id", eventKey);
                            startActivity(clickedEvent);
                            return;
                        }

                        isJoinEvent = true;
                        mJoinEvent.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (isJoinEvent) {
                                    if (dataSnapshot.child(eventKey).hasChild(mCurrentUser.getUid())) {
                                        mJoinEvent.child(eventKey).child(mCurrentUser.getUid()).removeValue();
                                        //Toast.makeText(getActivity(), "You have joined this event.", Toast.LENGTH_LONG).show();
                                        isJoinEvent = false;
                                    } else {
                                        mJoinEvent.child(eventKey).child(mCurrentUser.getUid()).setValue(User.getInstance().getDisplayName());
                                        if(MainActivity.notification == true){
                                            String title = "Dear " + model.getUsername() + "\n\n";
                                            String message = User.getInstance().getDisplayName() + " has joined your event【 "
                                                    +model.getCourse() + " : "+model.getTitle()+" 】" + "\n\n";
                                            String from = "Thanks,\nYour StudyBuddy Team\n";

                                            // Send email to admin
                                            BackgroundMail bm = new BackgroundMail(getContext());
                                            String subject = model.getUsername()+", "+User.getInstance().getDisplayName() + " has joined your event.";
                                            String email = "studybuddycs446@gmail.com";
                                            String password = "studybuddy123";
                                            bm.setGmailUserName(email);
                                            bm.setGmailPassword(password);
                                            bm.setMailTo(model.getQuestId()+"@edu.uwaterloo.ca");
                                            bm.setFormSubject(subject);
                                            bm.setFormBody(title + message + from);
                                            bm.send();
                                        }
                                        isJoinEvent = false;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };

    }

    public void Setup_UsertableListener(){
        FirebaseUserInfo.getUsersTable().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User.getInstance().setmUserTable_DS(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        /*List courses = UserInfo.getCourses();
        int numCourses = courses.size();*/


        View rootView = inflater.inflate(R.layout.fragment_events_list, container, false);

        rv = (RecyclerView)rootView.findViewById(R.id.events_list_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(fbRecyclerAdapter);

        return rootView;
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
