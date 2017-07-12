package uw.studybuddy.UserProfile;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private DataSnapshot dataSnapshot_FG;
    private DataSnapshot dataSnapshot_FG_Name;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FriendListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FriendListFragment newInstance(int columnCount) {
        FriendListFragment fragment = new FriendListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        Setup_namelistListener();
        Setup_UsertableListener();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendlist_list, container, false);


        bSearch = (Button)view.findViewById(R.id.find_friends_search_button_FG);

        etSearch = (EditText) view.findViewById(R.id.find_friends_name_FG);
        bSearch.setOnClickListener(this);


        //get friend list here
        String[] Friendlist = {"Yuna", "Waterloo", "1111", "hello", "noooooo", "just for texst", "lol", "I am sleepy", "I am sosososososo sleepy"};

        // Set the adapter
        if (true) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyFriendListRecyclerViewAdapter(FriendListFragment.this, Friendlist, mListener));
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
        if(temp == ""){
            //do nothing
            etSearch.setHintTextColor(getResources().getColor(R.color.errorhint));
            etSearch.setTextColor(getResources().getColor(R.color.errorhint));

        }else {
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.find_friend_dialog);
            dialog.setTitle("");

            //set the customeer dialog component
            TextView text_name = (TextView) dialog.findViewById(R.id.friend_name_DG);
            TextView text_aboutme = (TextView)dialog.findViewById(R.id.friend_about_me_DG);
            ImageView image = (ImageView)dialog.findViewById(R.id.friend_photo_DG);
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
                //should set a sorry image for it later
            }else{
                text_name.setText(Userholder.getdisplay_name());
                text_aboutme.setText(Userholder.getabout_me());
                course.setText(transfer_list_courseInfo_toString(Userholder.getcourse()));
            }



            Button dialogOKButton = (Button) dialog.findViewById(R.id.OK_Dialog_bt);
            Button dialogAddFriendButton = (Button) dialog.findViewById(R.id.add_Friend_bt);
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
                        if( Userholder.getquest_id() != FirebaseUserInfo.get_QuestId()) {
                            FirebaseUserInfo.getCurrentUserRef().child(FirebaseUserInfo.table_friend).child(Userholder.getquest_id()).setValue(true);
                        }
                    }
                    dialog.dismiss();
                }
            });
            //set the dialog things here
            dialog.show();
            //show the diaglog

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

    public static String transfer_list_courseInfo_toString(List<CourseInfo> list){
        String result = "Course : ";
        if(list == null){
            return result;
        }
        for(CourseInfo value : list){
            result = result + " " + value.getSubject() + value.getCatalogNumber() + " ";
        }
        return result;
    }
}
