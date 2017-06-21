package uw.studybuddy;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import uw.studybuddy.UserProfile.FriendListArrayAdapter;

public class FriendList extends ListActivity {

    static final String[] FriendNameList =
            new String[]{"Waterloo", "Yuna"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_friend_list);
        setListAdapter(new FriendListArrayAdapter(this, FriendNameList));

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        //get selected items
        String selectValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectValue, Toast.LENGTH_LONG).show();
    }

}
