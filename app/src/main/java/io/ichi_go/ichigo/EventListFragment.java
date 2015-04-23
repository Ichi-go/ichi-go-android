package io.ichi_go.ichigo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Russell on 4/23/2015.
 */

public class EventListFragment extends Fragment {
    private RecyclerView recyclerView;
    private EventListAdapter adapter;

    public static EventListFragment getInstance() {
        EventListFragment eventListFragment = new EventListFragment();
        return eventListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.event_list);
        adapter = new EventListAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public static List<Event> getData() {
        List<Event> data = new ArrayList<>();
        String[] names = {"Mommy Meetup", "Daddy Daycare", "Puppies in Puddles", "Turf War"};
        String[] descriptions = {"Hang out with mommies!", "Bring your kids! No pedos!", "CUTEST EVER", "No cops allowed"};
        for (int i = 0; i < names.length; i++) {
            Event current = new Event(i, names[i], descriptions[i], 0.0, 0.0, "Basic Location");
            data.add(current);
        }
        return data;
    }


}
