package uw.studybuddy.UserProfile;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

    public MyFriendListRecyclerViewAdapter(FriendListFragment fragment, List<String> Friendlist,  OnListFragmentInteractionListener listener) {
        mValues = Friendlist;
        mListener = listener;
        context = fragment.getContext();
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
        //holder.mContentView.setImageResource(mValues.get(position).content);

        holder.mIdView.setText(mValues.get(position));



        //change the icon based on name
        String s = mValues.get(position);
        System.out.println(s);

        if(s.equals("Yuna")){
            holder.mContentView.setImageResource(R.drawable.friend1);
        }else{
            holder.mContentView.setImageResource(R.drawable.friend2);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = holder.mIdView.getText().toString();
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.find_friend_dialog);
                    dialog.setTitle("");
                    //set the customeer dialog component
                    TextView text_name = (TextView) dialog.findViewById(R.id.friend_name_DG);
                    TextView text_aboutme = (TextView)dialog.findViewById(R.id.friend_about_me_DG);
                    ImageView image = (ImageView)dialog.findViewById(R.id.friend_photo_DG);
                    //default photo for now
                    image.setImageResource(R.drawable.friend1);
                    //now for testing
                    text_name.setText("hello");

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
                            dialog.dismiss();
                        }
                    });

                    if(temp == ""){

                    }else {
                        dialog.show();
                        //show the diaglog

                    }

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
        public final ImageView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.label_FG);
            mContentView = (ImageView) view.findViewById(R.id.logo_FG);
        }

        @Override
        public String toString() {
            return super.toString() + " '"  + "'";
        }
    }



}
