package io.ichi_go.ichigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.ichi_go.ichigo.Event;
import io.ichi_go.ichigo.R;


public class DisplayAllEventsActivity extends ActionBarActivity {

    private LinkedHashMap<String, List<Event>> channelHashmap;
    private List<String> channels;
    private ExpandableListView exp_list;
    private ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Temporary Way to populate list
        SQLdb info = new SQLdb(this);
        info.open();
        ArrayList<Event> listOfEvents = info.getData2();
        info.dumpDB();
        info.close();
        ArrayList<Event> list1 = new ArrayList<>();
        ArrayList<Event> list2 = new ArrayList<>();
        ArrayList<Event> list3 = new ArrayList<>();
        ArrayList<Event> list4 = new ArrayList<>();
        ArrayList<Event> list5 = new ArrayList<>();

        for (int i = 0; i < listOfEvents.size() - 4; i=i+5) {
            list1.add(listOfEvents.get(i));
            list2.add(listOfEvents.get(i+1));
            list3.add(listOfEvents.get(i+2));
            list4.add(listOfEvents.get(i+3));
            list5.add(listOfEvents.get(i+4));
        }


        exp_list = (ExpandableListView) findViewById(R.id.expandable_list);
        channelHashmap = new LinkedHashMap<>();
        channelHashmap.put("Channel1", list1);
        channelHashmap.put("Channel2", list2);
        channelHashmap.put("Channel3", list3);
        channelHashmap.put("Channel4", list4);
        channelHashmap.put("Channel5", list5);

        channels = new ArrayList<>(channelHashmap.keySet());
        System.out.println("Keyset: " + channels);
        expandableListAdapter = new ExpandableListAdapter(this, channelHashmap, channels);
        exp_list.setAdapter(expandableListAdapter);
        exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                Intent i = new Intent(parent.getContext(), DisplayEventActivity.class);
                i.putExtra("currentEvent", channelHashmap.get(channels.get(groupPosition)).get(childPosition));
                startActivity(i);

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

}
