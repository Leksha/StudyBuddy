package uw.studybuddy.Events;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uw.studybuddy.CourseInfo;
import uw.studybuddy.HomePageFragments.DisplayCourses;
import uw.studybuddy.HomePageFragments.HomePage;
import uw.studybuddy.LoginAndRegistration.LoginActivity;
import uw.studybuddy.LoginAndRegistration.RegisterActivity;
import uw.studybuddy.R;
import uw.studybuddy.UserProfile.UserInfo;


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
    private DatabaseReference mDatabaseChat;
    private DatabaseReference mDatabaseNotification;
    private Query mQueryCourse;

    UserInfo User;

    private RecyclerView rv;
    private String TAG = "EventsListRVFragment";

    private DatabaseReference mJoinEvent;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        mJoinEvent = FirebaseDatabase.getInstance().getReference().child("Participants");
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("EventChat");
        mDatabaseNotification = FirebaseDatabase.getInstance().getReference().child("Notifications");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabaseCourse = FirebaseDatabase.getInstance().getReference().child("Event");
        if(DisplayCourses.clickedCourse.equals("")){
            mQueryCourse = mDatabaseCourse;
        } else {
            mQueryCourse = mDatabaseCourse.orderByChild("course").equalTo(DisplayCourses.clickedCourse);
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
                //viewHolder.setLocation(model.getLocation());
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

                        if(viewHolder.BjoinEvent.getText() == "Review Event"){
                            Intent clickedEvent = new Intent(getActivity(), EventDescription.class);
                            clickedEvent.putExtra("event_id", eventKey);
                            startActivity(clickedEvent);
                            return;
                        }

                        isJoinEvent = true;
                        mJoinEvent.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (isJoinEvent)
                                    if (dataSnapshot.child(eventKey).hasChild(mCurrentUser.getUid())) {
                                        mJoinEvent.child(eventKey).child(mCurrentUser.getUid()).removeValue();
                                        mDatabaseChat.child(model.getCourse() + ": " + model.getTitle()).removeValue();
                                        //Toast.makeText(getActivity(), "You have joined this event.", Toast.LENGTH_LONG).show();
                                        mDatabase.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String questId = dataSnapshot.child(eventKey).child("questId").getValue().toString();
                                                mDatabaseNotification.child(questId).child(eventKey).child(mCurrentUser.getUid()).removeValue();
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        isJoinEvent = false;
                                    } else {
                                        mJoinEvent.child(eventKey).child(mCurrentUser.getUid()).setValue(User.getInstance().getDisplayName());
                                        Map<String, Object> map = new HashMap<String, Object>();
                                        map.put(model.getCourse() + ": " + model.getTitle(), "");
                                        mDatabaseChat.updateChildren(map);
                                        mDatabase.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String questId = dataSnapshot.child(eventKey).child("questId").getValue().toString();
                                                mDatabaseNotification.child(questId).child(eventKey).child(mCurrentUser.getUid()).setValue(User.getInstance().getDisplayName());
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        isJoinEvent = false;
                                    }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
                /*viewHolder.BleaveEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isJoinEvent = false;
                        mJoinEvent.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!isJoinEvent) {
                                    if (dataSnapshot.child(eventKey).hasChild(mCurrentUser.getUid())) {
                                        mJoinEvent.child(eventKey).child(mCurrentUser.getUid()).removeValue();
                                        isJoinEvent = true;
                                    } else {
                                        Toast.makeText(getActivity(), "You did not join this event.", Toast.LENGTH_LONG).show();
                                        isJoinEvent = true;
                                    }
                                }
                            }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                });*/
            }
        };

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

    // can we show event earlier?

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
