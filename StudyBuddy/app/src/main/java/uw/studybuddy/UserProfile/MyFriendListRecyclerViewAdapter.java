package uw.studybuddy.UserProfile;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

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

    public MyFriendListRecyclerViewAdapter(FriendListFragment fragment, List<String> Friendlist,  OnListFragmentInteractionListener listener) {
        mValues = Friendlist;
        mListener = listener;
        context = fragment.getContext();
        Setup_UsertableListener();

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

        holder.mIdView.setText(mValues.get(position));



        //change the icon based on name
        String s = mValues.get(position);
        System.out.println(s);

        if(s.equals("Yuna")){
            holder.mPhotoView.setImageResource(R.drawable.friend1);
        }else{
            holder.mPhotoView.setImageResource(R.drawable.friend2);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = holder.mIdView.getText().toString();
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
                    image.setImageResource(R.drawable.friend1);
                    //now for testing

                    final UserPattern Userholder = new UserPattern();

                    Userholder.get_user(dataSnapshot_FG,temp);



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
                                FirebaseUserInfo.getCurrentUserRef().child(FirebaseUserInfo.table_friend).child(friendname).setValue(friendname);
                                Toast.makeText(context, "Success: " + friendname + " is on friend list now",
                                            Toast.LENGTH_LONG).show();
                            }
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





}
