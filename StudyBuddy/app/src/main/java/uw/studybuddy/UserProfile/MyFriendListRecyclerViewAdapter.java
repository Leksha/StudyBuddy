package uw.studybuddy.UserProfile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uw.studybuddy.R;
import uw.studybuddy.UserProfile.FriendListFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFriendListRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendListRecyclerViewAdapter.ViewHolder> {

    private final String[] mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyFriendListRecyclerViewAdapter(String[] items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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

        holder.mIdView.setText(mValues[position]);



        //change the icon based on name
        String s = mValues[position];
        System.out.println(s);

        if(s.equals("Yuna")){
            holder.mContentView.setImageResource(R.drawable.friend1);
        }else{
            holder.mContentView.setImageResource(R.drawable.friend2);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.length;
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
