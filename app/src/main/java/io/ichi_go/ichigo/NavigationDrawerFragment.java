package io.ichi_go.ichigo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Activity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavDrawerAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    public static ArrayList<Event> biblio = new ArrayList<Event>();
    private int menuflag = 0;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final LayoutInflater inflater2 = inflater;
        final ViewGroup container2 = container;
        final Bundle savedInstanceState2 = savedInstanceState;
        menuflag = 0;

        getData2(getActivity());

        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawer_list);
        adapter = new NavDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

if(menuflag == 0) {
    if (position == 0) {

        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(layout.findViewById(R.id.drawer_list));


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
                        startActivity(new Intent(getActivity(), NewEventActivity.class));
                    }
                });
            }

        }).start();


    }
    if (position == 1) {


        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(layout.findViewById(R.id.drawer_list));

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
                        startActivity(new Intent(getActivity(), MyEventsActivity2.class));
                    }
                });
            }

        }).start();


    }

    if (position == 2) {

        menuflag = 1;

        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(layout.findViewById(R.id.drawer_list));

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

                        // startActivity(new Intent(getActivity(), MainMyEventsActivity.class));
                        //onCreateView2(inflater2,container2,savedInstanceState2);
                        adapter = new NavDrawerAdapter(getActivity(), getData2(getActivity()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//                        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
//                            @Override
//                            public void onClick(View view, int position) {
//
//
//                                YoYo.with(Techniques.Tada)
//                                        .duration(700)
//                                        .playOn(layout.findViewById(R.id.drawer_list));
//
//                                displayEvent.setName(biblio.get(position).getName());
//                                displayEvent.setDesciption(biblio.get(position).getDescription());
//
//
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            Thread.sleep(700);
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                        getActivity().runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                startActivity(new Intent(getActivity(), DisplayEventActivity.class));
//                                            }
//                                        });
//                                    }
//
//                                }).start();
//
//
//                            }
//
//
//                            @Override
//                            public void onLongClick(View view, int position) {
//
//                            }
//                        }));

                        Log.d("MyEvent", "MyEvents View selected");
                        Log.d("MyEvent", "MyEvents View selected");
                        Log.d("MyEvent", "MyEvents View selected");

                    }
                });
            }

        }).start();

    }


    if (position == 3) {


        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(layout.findViewById(R.id.drawer_list));


    }

}else {

    YoYo.with(Techniques.Tada)
            .duration(700)
            .playOn(layout.findViewById(R.id.drawer_list));

    displayEvent.setName(biblio.get(position).getName());
    displayEvent.setDesciption(biblio.get(position).getDescription());


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
                    startActivity(new Intent(getActivity(), DisplayEventActivity.class));
                }
            });
        }

    }).start();

}

            }



            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;
    }



    public View onCreateView2(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("MyEvent","onCreateView2");
        Log.d("MyEvent","onCreateView2");
        Log.d("MyEvent","onCreateView2");



        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_my_events3, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawer_list);
        adapter = new NavDrawerAdapter(getActivity(), getData2(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {


                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(layout.findViewById(R.id.drawer_list));

                displayEvent.setName(biblio.get(position).getName());
                displayEvent.setDesciption(biblio.get(position).getDescription());

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
                                startActivity(new Intent(getActivity(), DisplayEventActivity.class));
                            }
                        });
                    }

                }).start();



            }



            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;
    }

    public static List<NavItem> getData() {
        List<NavItem> data = new ArrayList<>();
        String[] titles = {"New Event", "My Events 2", "My Events 3", "Test 4"};
        for (int i = 0; i < titles.length; i++) {
            NavItem current = new NavItem();
            current.iconId = R.drawable.ic_launcher_strawberry;
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }


    public static List<NavItem> getData2( Context itself) {


        List<NavItem> data = new ArrayList<>();


        SQLdb info = new SQLdb(itself);
        info.open();

        ArrayList<Event> biblio2 = info.getData2();

        info.dumpDB();

        if(UpdateFlag.getUpdate() == 1) {
            info.initDB();
        }

        info.populate();

        info.close();


        if(!biblio.equals(biblio2)){

            biblio = biblio2;
        }


        Event sco2;
        String name;

        int len1 = 8;
        int len2 = 8;

        for (int i = 0; i < biblio.size(); i++) {
            sco2 = biblio.get(i);
            if(sco2.getName().length() < 8){
                len1 = sco2.getName().length();
            }
            if(sco2.getDescription().length() < 8){
                len2 = sco2.getDescription().length();
            }
            name = String.format("%15s", sco2.getName().substring(0,len1));

            NavItem current = new NavItem();
            current.iconId = R.drawable.ic_launcher_strawberry;
            current.title = name;
            data.add(current);

        }


        return data;
    }



    public void setUp(int fragmentID, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(child != null && clickListener != null){
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if(child != null && clickListener != null && gestureDetector.onTouchEvent(motionEvent)){
                clickListener.onClick(child, recyclerView.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }
}
