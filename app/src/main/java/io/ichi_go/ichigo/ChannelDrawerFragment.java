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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.ichi_go.ichigo.data.controller.EventManager;
import io.ichi_go.ichigo.data.model.Event;

/**
 * The drawer fragment that displays channels
 */
public class ChannelDrawerFragment extends Fragment {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private LinkedHashMap<String, List<Event>> channelHashmap;
    private List<String> channels;
    private ExpandableListView exp_list;
    private ExpandableListAdapter expandableListAdapter;

    /**
     * Required empty public constructor
     */
    public ChannelDrawerFragment() {
        // Required empty public constructor
    }

    /**
     * This method creates the activity every time
     * @param savedInstanceState Used if instance state was saved
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates the view for the channel drawer
     * @param inflater The inflater of the view
     * @param container the container of the view
     * @param savedInstanceState Used if instance state was saved
     * @return the view that is being created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_channel_drawer, container, false);

        EventManager eventManager = EventManager.getInstance();
        ArrayList<Event> listOfEvents = eventManager.getEvents();

        //Until Channel manager exists only have 2 channels
        channelHashmap = new LinkedHashMap<>();
        channelHashmap.put("NMT Channel", listOfEvents);

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
                        mDrawerLayout.closeDrawer(Gravity.RIGHT);
                        Event clickedEvent = channelHashmap.get(channels.get(groupPosition)).get(childPosition);
                        MapsActivity mapsActivity = (MapsActivity) getActivity();
                        mapsActivity.goToLocation(clickedEvent);
                        return false;
                    }
                }
        );
        return layout;
    }

    /**
     * Sets up the drawer layout
     * @param drawerLayout the drawer layout to be set up
     * @param toolbar the toolbar that is modified when the drawer is opened/closed (toggled)
     */
    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
