package io.ichi_go.ichigo;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Event currentEvent = getData().get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(getActivity(), ViewEventActivity.class);
                                i.putExtra("currentEvent",(Parcelable) currentEvent);
                                startActivity(i);
                            }
                        });
                    }

                }).start();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(motionEvent)) {
                clickListener.onClick(child, recyclerView.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }
    }


    public static List<Event> getData() {
        //
        // This is where we would maybe need the server to obtain the list
        //
        List<Event> data = new ArrayList<>();
        String[] names = {"Mommy Meetup", "Daddy Daycare", "Puppies in Puddles", "Turf War"};
        String[] descriptions = {"Hang out with mommies!", "Bring your kids! No pedos!", "CUTEST EVER", "No cops allowed"};
        for (int i = 0; i < names.length; i++) {
            Event current = new Event(i, names[i], descriptions[i], 0.0, 0.0, "Basic Location");
            data.add(current);
        }
        return data;
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

}
