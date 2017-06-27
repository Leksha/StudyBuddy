package uw.studybuddy;

import android.content.Context;
import android.hardware.input.InputManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import uw.studybuddy.R;

/**
 * Created by Yuna on 17/6/17.
 */

public class FriendListArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public FriendListArrayAdapter(Context context, String[] values){
        super(context, R.layout.activity_friend_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_friend_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values[position]);


        //change the icon based on name
        String s = values[position];
        System.out.println(s);

        if(s.equals("Yuna")){
            imageView.setImageResource(R.drawable.friend1);
        }else{
            imageView.setImageResource(R.drawable.friend2);
        }

        return rowView;

    }

}
