package io.ichi_go.ichigo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.model.LatLng;
import com.jmpergar.awesometext.AwesomeTextHandler;

import static io.ichi_go.ichigo.R.id.event_description;
import static io.ichi_go.ichigo.R.id.event_name;


public class DisplayEventActivity extends ActionBarActivity {

    private static final String HASHTAG_PATTERN = "(#[\\p{L}0-9-_]+)";
    private static final String MENTION_PATTERN = "(@[\\p{L}0-9-_]+)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv = (TextView) findViewById(R.id.display_event_name);
        TextView tv2 = (TextView) findViewById(R.id.display_event_description);

//        tv.setTypeface(Typeface.MONOSPACE);
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        String awesome = "@";
        awesome = displayEvent.getName();

        tv.setText(awesome);

        awesome = "@";
        awesome = displayEvent.getDesciption();
        tv2.setText(awesome);



//        TextView textView = (TextView) findViewById(R.id.display_event_name2);
//        EditText editText = (EditText) findViewById(R.id.display_event_description2);

        AwesomeTextHandler awesomeTextViewHandler = new AwesomeTextHandler();
        awesomeTextViewHandler
                .addViewSpanRenderer(HASHTAG_PATTERN, new HashtagsSpanRenderer())
                .addViewSpanRenderer(MENTION_PATTERN, new MentionSpanRenderer());
//                .setView(textView);

//        AwesomeTextHandler awesomeEditTextHandler = new AwesomeTextHandler();
//        awesomeEditTextHandler
//                .addViewSpanRenderer(HASHTAG_PATTERN, new HashtagsSpanRenderer())
//                .addViewSpanRenderer(MENTION_PATTERN, new MentionSpanRenderer())
//                .setView(editText);

        awesomeTextViewHandler.setView(tv);
        awesomeTextViewHandler.setView(tv2);

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

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public void createEvent(View v) {
        if(v.getId() == R.id.create_event_button) {

            YoYo.with(Techniques.RotateOut)
                    .duration(700)
                    .playOn(findViewById(R.id.create_event_button));


            final ActionBarActivity itself = this;



            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            NavUtils.navigateUpFromSameTask(itself);

                        }
                    });
                }

            }).start();




        }
    }


}
