package uw.studybuddy.UserProfile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import uw.studybuddy.ChatActivity;
import uw.studybuddy.Events.EventCreation;
import uw.studybuddy.MainActivity;
import uw.studybuddy.R;
import uw.studybuddy.UserProfile.FriendListFragment.OnListFragmentInteractionListener;

import static android.app.PendingIntent.getActivity;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFriendListRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendListRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private final List<String> mValues;
    private final OnListFragmentInteractionListener mListener;
    private DataSnapshot dataSnapshot_FG;
    private UserInfo user;
    private String chatKey;

    UserInfo User;

    private DatabaseReference mDatabaseChat;

    public MyFriendListRecyclerViewAdapter(FriendListFragment fragment, List<String> Friendlist,  OnListFragmentInteractionListener listener) {
        mValues = Friendlist;
        mListener = listener;
        context = fragment.getContext();
        Setup_UsertableListener();
        user = UserInfo.getInstance();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_friendlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mPhotoView.setImageResource(mValues.get(position).content);
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("FriendChat");



        //change the icon based on name
        String s = mValues.get(position);
        System.out.println(s);







        final UserPattern Userholder = new UserPattern();

        if(dataSnapshot_FG == null){
            Log.d("UserTableFG", "fail get FG");
            //need change
            dataSnapshot_FG= user.getmUserTable_DS();
        }else {
            //update the dataSnapshot_FG
            user.setmUserTable_DS(dataSnapshot_FG);
        }
        Userholder.get_user(dataSnapshot_FG,mValues.get(position));


        String Uri_temp = Userholder.getImage();

        if(Uri_temp == null){
            holder.mPhotoView.setImageResource(R.drawable.friend1);
        }else{
            Picasso.with(context).load(Uri_temp).into(holder.mPhotoView);
        }


        if(Userholder.getdisplay_name() == null){
            holder.mIdView.setText("The User is not exist " + mValues.get(position) );
        }else{
            holder.mIdView.setText(Userholder.getdisplay_name());
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);

                //display the dialog
                    final Dialog dialog = new Dialog(context);
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

                    //now for testing


                    image.setImageResource(R.drawable.friend1);


                    //FirebaseUserInfo.listener_trigger();

                    if(Userholder.getdisplay_name() == null){

                        text_aboutme.setText("Sorry the User you search is not exist");
                        text_name.setText("");
                        course.setText("");
                        Toast.makeText(context, "Sorry the User you search is not exist",
                                Toast.LENGTH_LONG).show();
                        //should set a sorry image for it later
                    }else{
                        text_name.setText(Userholder.getdisplay_name());
                        text_aboutme.setText(Userholder.getabout_me());
                        course.setText(UserPattern.transfer_list_courseInfo_toString(Userholder.getcourse()));
                        dialogAddFriendButton.setText("Chat");


                        //dialogAddFriendButton.setClickable(false);
                        //dialogAddFriendButton.setVisibility(dialogAddFriendButton.GONE);
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
                            /*if(Userholder.getdisplay_name()!= null ){
                                //if the friend is not user himself/herself
                                String friendname = Userholder.getquest_id().toString();
                                FirebaseUserInfo.getCurrentUserRef().child(FirebaseUserInfo.table_friend).child(friendname).setValue(friendname);
                                Toast.makeText(context, "Success: " + friendname + " is on friend list now",
                                            Toast.LENGTH_LONG).show();
                            }*/
                            HashMap<String, Object> dataMap = new HashMap<String, Object>();
                            String cur = User.getInstance().getDisplayName();
                            String friend = Userholder.getdisplay_name();
                            if(friend.compareTo(cur) < 0){
                                chatKey = friend + " and " + cur;
                            } else {
                                chatKey = cur + " and " + friend;
                            }
                            mDatabaseChat.child(chatKey).updateChildren(dataMap);
                            Intent intent = new Intent(context, ChatActivity.class);
                            intent.putExtra("chatname", chatKey);
                            intent.putExtra("friendname", friend);
                            context.startActivity(intent);
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mPhotoView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.label_FG);
            mPhotoView = (ImageView) view.findViewById(R.id.logo_FG);
        }

        @Override
        public String toString() {
            return super.toString() + " '"  + "'";
        }
    }



    public void Setup_UsertableListener(){
        FirebaseUserInfo.getUsersTable().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot_FG =dataSnapshot;
                user.setmUserTable_DS(dataSnapshot_FG);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





}
