package uw.studybuddy.UserProfile;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uw.studybuddy.CourseInfo;
import uw.studybuddy.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FriendListFragment extends Fragment implements Button.OnClickListener
{
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public Button bSearch;
    public EditText etSearch;
    //Usertable
    private DataSnapshot dataSnapshot_FG;
    //Friendlist
    private DataSnapshot dataSnapshot_FG_Name;

    private DataSnapshot dataSnapshot_FriendList_FG;

    private UserInfo user;

    ViewGroup container_FG;

    String CurrentID ;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public FriendListFragment(){
        set_up_friendlist_Listener();
    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public FriendListFragment newInstance(int columnCount) {
        FriendListFragment fragment = new FriendListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        set_up_friendlist_Listener();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = UserInfo.getInstance();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        CurrentID = FirebaseUserInfo.get_QuestId().toString();
        Setup_namelistListener();
        Setup_UsertableListener();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_friendlist_list, container, false);
        container_FG = container;

        bSearch = (Button)view.findViewById(R.id.find_friends_search_button_FG);

        etSearch = (EditText) view.findViewById(R.id.find_friends_name_FG);
        bSearch.setOnClickListener(this);


        //get friend list here


        List<String> Friend_temp = new ArrayList<String>();

        if(dataSnapshot_FriendList_FG == null){
            Log.d("FriendList", "fail get FG");
            dataSnapshot_FriendList_FG = user.getmFriendlist_DS();
        }else {
            user.setmFriendlist_DS(dataSnapshot_FriendList_FG);
        }

        //now try to get the friendlist from firebase


        Friend_temp = FirebaseUserInfo.get_friend_list_fromDatabase(dataSnapshot_FriendList_FG);
        // Set the adapter
        if (true) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyFriendListRecyclerViewAdapter(FriendListFragment.this, Friend_temp, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        String temp = etSearch.getText().toString();
        //friend dialog
        if(temp.equals("")){
            //do nothing
            etSearch.setHintTextColor(getResources().getColor(R.color.errorhint));
            return;

        }else {
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.find_friend_dialog);
            dialog.setTitle("");

            //set the customeer dialog component
            TextView text_name = (TextView) dialog.findViewById(R.id.friend_name_DG);
            TextView text_aboutme = (TextView)dialog.findViewById(R.id.friend_about_me_DG);
            ImageView image = (ImageView)dialog.findViewById(R.id.friend_photo_DG);
            Button dialogOKButton = (Button) dialog.findViewById(R.id.OK_Dialog_bt);
            Button dialogAddFriendButton = (Button) dialog.findViewById(R.id.add_Friend_bt);
            //default photo for now
           TextView course = (TextView) dialog.findViewById(R.id.couse_DG);
            image.setImageResource(R.drawable.friend1);
            //now for testing

            final UserPattern Userholder = new UserPattern();

            Userholder.get_user(dataSnapshot_FG,temp);

            if(Userholder.getdisplay_name() == null){
                String key = get_key_from_namelist_byName(temp);
                if(key != null) {
                    Userholder.get_user(dataSnapshot_FG,key);
                }
            }


            //FirebaseUserInfo.listener_trigger();

            if(Userholder.getdisplay_name() == null){

                text_aboutme.setText("Sorry the User you search is not exist");
                text_name.setText("");
                course.setText("");
                Toast.makeText(getActivity(), "Sorry the User you search is not exist",
                        Toast.LENGTH_LONG).show();
                //should set a sorry image for it later
            }else{
                text_name.setText(Userholder.getdisplay_name());
                text_aboutme.setText(Userholder.getabout_me());
                course.setText(UserPattern.transfer_list_courseInfo_toString(Userholder.getcourse()));
                dialog.show();

            }




            dialogOKButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialogAddFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //The friend is added  to the friendlist:
                    if(Userholder.getdisplay_name()!= null ){
                        //if the friend is not user himself/herself
                        String friendname = Userholder.getquest_id().toString();
                       if(!friendname.equals(CurrentID)){

                            FirebaseUserInfo.getCurrentUserRef().child(FirebaseUserInfo.table_friend).child(friendname).setValue(friendname);
                           Toast.makeText(getActivity(), "Success: " + friendname + " is on friend list now",
                                   Toast.LENGTH_LONG).show();

                       }else{
                           Toast.makeText(getActivity(), "Adding friend Failed",
                                   Toast.LENGTH_LONG).show();
                       }
                    }
                    dialog.dismiss();
                    reloadFragment();
                }
            });

        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Uri uri);
    }

    public void Setup_UsertableListener(){
        FirebaseUserInfo.getUsersTable().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot_FG =dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Setup_namelistListener(){
        FirebaseUserInfo.get_namelist_ref().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot_FG_Name = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public  String get_key_from_namelist_byName(String name){

        String key  = dataSnapshot_FG_Name.child(name).getValue(String.class);
        return key;
    }



    public void set_up_friendlist_Listener(){
        FirebaseUserInfo.getCurrentUserRef().child(FirebaseUserInfo.table_friend)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        dataSnapshot_FriendList_FG = dataSnapshot;
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                }
                );
    }

    //well....
    private void reloadFragment() {
        Log.d("ReLoad: ", "reloadFragment()");
        FriendListFragment f = new FriendListFragment();
        getFragmentManager().beginTransaction().replace(R.id.flContent, f).commit();
    }

}
