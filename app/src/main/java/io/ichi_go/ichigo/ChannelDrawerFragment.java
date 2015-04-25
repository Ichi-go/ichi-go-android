package io.ichi_go.ichigo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ichigo on 4/25/15.
 */
public class ChannelDrawerFragment extends Fragment {


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private LinkedHashMap<String, List<Event>> channelHashmap;
    private List<String> channels;
    private ExpandableListView exp_list;
    private ExpandableListAdapter expandableListAdapter;

    public ChannelDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_channel_drawer, container, false);

        //
        //Temporary Way to populate list
        //
        SQLdb info = new SQLdb(getActivity());
        info.open();
        ArrayList<Event> listOfEvents = info.getData2();
        info.dumpDB();
        info.close();
        ArrayList<Event> list1 = new ArrayList<>();
        ArrayList<Event> list2 = new ArrayList<>();

        for (int i = 0; i < listOfEvents.size() /2; i++) {
            list1.add(listOfEvents.get(i*2));
            list2.add(listOfEvents.get(i*2 + 1));
        }
        channelHashmap = new LinkedHashMap<>();
        channelHashmap.put("Channel1", list1);
        channelHashmap.put("Channel2", list2);

        channels = new ArrayList<>(channelHashmap.keySet());

        exp_list = (ExpandableListView) layout.findViewById(R.id.expandable_list);
        expandableListAdapter = new ExpandableListAdapter(getActivity(), channelHashmap, channels);

        exp_list.setAdapter(expandableListAdapter);
        exp_list.setOnChildClickListener(
                new ExpandableListView.OnChildClickListener()

                {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                                int childPosition, long id) {

                        Intent i = new Intent(parent.getContext(), DisplayEventActivity.class);
                        i.putExtra("currentEvent", channelHashmap.get(channels.get(groupPosition)).get(childPosition));
                        startActivity(i);

                        return false;
                    }
                }
        );
        return layout;
    }




    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }


}
