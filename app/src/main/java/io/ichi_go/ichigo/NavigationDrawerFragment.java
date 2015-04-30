package io.ichi_go.ichigo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;


/**
 * Navigation drawer fragment that manages the navigation drawer
 */
public class NavigationDrawerFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavDrawerAdapter adapter;
    private View containerView;

    /**
     * The required empty public constructor
     */
    public NavigationDrawerFragment() {
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
     * Creates the view for the navigation drawer
     * @param inflater The inflater of the view
     * @param container the container of the view
     * @param savedInstanceState Used if instance state was saved
     * @return the view that is being created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawer_list);
        adapter = new NavDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //First option in the navigation drawer was selected
                if (position == 0) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(getActivity().findViewById(R.id.drawer_list));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(700);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),
                                            "Click and hold to choose location",
                                            Toast.LENGTH_SHORT).show();

                                    mDrawerLayout.closeDrawer(Gravity.START);
                                }
                            });
                        }

                    }).start();
                }

                //Second item in the navigation drawer was selected
                if (position == 1) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(getActivity().findViewById(R.id.drawer_list));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(700);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });
                        }

                    }).start();
                }

                //Third item in the navigation drawer was selected
                if (position == 2) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(getActivity().findViewById(R.id.drawer_list));
                }

                if (position == 3) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(getActivity().findViewById(R.id.drawer_list));
                } else {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(getActivity().findViewById(R.id.drawer_list));
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;
    }

    /**
     * Gets the list of NavItems that are used to populate each row of the navigation drawer
     * @return the list of NavItems
     */
    public static List<NavItem> getData() {
        List<NavItem> data = new ArrayList<>();
        String[] titles = {"Create Event", "My Events", "Friends", "Profile", "General", "Help & Feedback", "Settings"};
        for (String s : titles) {
            data.add(new NavItem(s, R.drawable.ic_launcher_strawberry));
        }
        return data;
    }

    /**
     * Sets up the navigation drawer
     * @param fragmentID the id of the fragment used to house the navigation drawer
     * @param drawerLayout the drawer layout to be set up
     * @param toolbar the toolbar that is modified when the drawer is opened/closed (toggled)
     */
    public void setUp(int fragmentID, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentID);
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

    /**
     * Sets up a GestureDetector on the navigation drawer
     */
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

    /**
     * Interface for a clicker listener
     */
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
