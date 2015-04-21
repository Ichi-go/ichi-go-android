package io.ichi_go.ichigo;

/**
 * Created by ahernand on 4/17/15.
 */




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.os.StrictMode.setThreadPolicy;

/**
 * Handles all the sql, and the sorting
 *
 * @author alexhernandez
 *
 */
public class SQLdb {

    public final static String KEY_ROWID = "_id";
    public final static String KEY_NAME = "persons_name";
    public final static String KEY_DESCRIPTION = "persons_description";
    public final static String KEY_LONG = "event_long";
    public final static String KEY_LAT = "event_lat";
    public final static String KEY_LOC = "event_location";


    private final static String DATABASE_NAME = "Eventdb6";
    private final static String DATABASE_TABLE = "eventTable";
    private final static String DATABASE_TABLE_prev = "eventTable4";
    private final static int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT NOT NULL, "
                    + KEY_LONG + " TEXT NOT NULL, "
                    + KEY_LAT + " TEXT NOT NULL, "
                    + KEY_LOC + " TEXT NOT NULL, "
                    + KEY_DESCRIPTION + " TEXT NOT NULL);");

            Log.d("DataBase", "database initialised");
            Log.d("DataBase","database initialised");
            Log.d("DataBase","database initialised");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }




    }

    public void initDB() throws SQLException {

        ourDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        ourDatabase.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT NOT NULL, "
                + KEY_LONG + " TEXT NOT NULL, "
                + KEY_LAT + " TEXT NOT NULL, "
                + KEY_LOC + " TEXT NOT NULL, "
                + KEY_DESCRIPTION + " TEXT NOT NULL);");

        Log.d("DataBase", "database initialised");
        Log.d("DataBase","database initialised");
        Log.d("DataBase","database initialised");


    }

    public SQLdb(Context c) {
        ourContext = c;
    }

    public SQLdb open() throws SQLException {

        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;

    }

    public void close() {
        ourHelper.close();
    }

    public long createEntry(String name, String description, String lat, String lon, String location) throws SQLException {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);

        cv.put(KEY_LAT, lat);
        cv.put(KEY_LONG, lon);
        cv.put(KEY_DESCRIPTION, description);
        cv.put(KEY_LOC, location);

        Log.d("DataBase", "entry created");
        Log.d("DataBase","entry created");
        Log.d("DataBase","entry created");

        return ourDatabase.insert(DATABASE_TABLE, null, cv);

    }

    public long sorted(String name, String description, String lat, String lon, String location) throws SQLException {

        ourDatabase.execSQL("SELECT " + KEY_DESCRIPTION + " FROM " + DATABASE_TABLE
                + " ORDER BY UPPER(" + KEY_DESCRIPTION + ");");

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_DESCRIPTION, description);
        cv.put(KEY_LAT, lat);
        cv.put(KEY_LONG, lon);
        cv.put(KEY_LOC, location);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);

    }

    public void dumpDB() throws SQLException {

        String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_LONG, KEY_LAT, KEY_DESCRIPTION, KEY_LOC };
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);
        String result = "";

        String delet = "";

        Event sco;
        Event sco2;

        ArrayList<Event> biblio = new ArrayList<Event>();
        //ArrayList<String> bob = new ArrayList<String>();


        int iName = c.getColumnIndex(KEY_NAME);
        int iDescription = c.getColumnIndex(KEY_DESCRIPTION);
        int iId = c.getColumnIndex(KEY_ROWID);
        int ilat = c.getColumnIndex(KEY_LAT);
        int ilon = c.getColumnIndex(KEY_LONG);
        int iloc = c.getColumnIndex(KEY_LOC);
        int count = 0;

        String name;
        String description;
        String latitude;
        String longitude;
        String all = "[";

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            sco = new Event( c.getString(iId), c.getString(iName), c.getString(iDescription), c.getString(ilat), c.getString(ilon), c.getString(iloc));
            biblio.add(sco);



        }

        Collections.sort(biblio);


        JSONArray array = new JSONArray();

        for(Event ev : biblio){
            array.put(ev.getJSON());
        }

        System.out.println(array.toString());

        Log.d("DataBase", "dump");
        Log.d("DataBase", "dump");
        Log.d("DataBase", "dump");

//        while (count < biblio.size()){
//
//            if(count != 0){
//                all = all + ",";
//            }
//
//            all = all + biblio.get(count);
//
//        }


    }


    public ArrayList<Event> getData2() throws SQLException {

        String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_LONG, KEY_LAT, KEY_DESCRIPTION, KEY_LOC };
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);
        String result = "";

        String delet = "";

        Event sco;
        Event sco2;

        ArrayList<Event> biblio = new ArrayList<Event>();
        //ArrayList<String> bob = new ArrayList<String>();


        int iName = c.getColumnIndex(KEY_NAME);
        int iDescription = c.getColumnIndex(KEY_DESCRIPTION);
        int iId = c.getColumnIndex(KEY_ROWID);
        int ilat = c.getColumnIndex(KEY_LAT);
        int ilon = c.getColumnIndex(KEY_LONG);
        int iloc = c.getColumnIndex(KEY_LOC);
        int count = 1;

        String name;
        String description;
        String latitude;
        String longitude;
        String all = "";

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            sco = new Event( c.getString(iId), c.getString(iName), c.getString(iDescription), c.getString(ilat), c.getString(ilon), c.getString(iloc));
            biblio.add(sco);



        }

        Collections.sort(biblio);



        return biblio;
    }

    public String getData() throws SQLException {

        String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_LONG, KEY_LAT, KEY_DESCRIPTION, KEY_LOC };
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);
        String result = "";

        String delet = "";

        Event sco;
        Event sco2;

        ArrayList<Event> biblio = new ArrayList<Event>();
        //ArrayList<String> bob = new ArrayList<String>();


        int iName = c.getColumnIndex(KEY_NAME);
        int iDescription = c.getColumnIndex(KEY_DESCRIPTION);
        int iId = c.getColumnIndex(KEY_ROWID);
        int ilat = c.getColumnIndex(KEY_LAT);
        int ilon = c.getColumnIndex(KEY_LONG);
        int iloc = c.getColumnIndex(KEY_LOC);
        int count = 1;

        String name;
        String description;
        String latitude;
        String longitude;
        String all;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


            sco = new Event( c.getString(iId), c.getString(iName), c.getString(iDescription), c.getString(ilat), c.getString(ilon), c.getString(iloc));
            biblio.add(sco);



        }

        Collections.sort(biblio);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            if(count >= 21){

                delet = biblio.get(0).getId();

                deleteEntry(delet);

            }

            int len1 = 8;
            int len2 = 8;

            sco2 = biblio.remove(0);

            if(sco2.getName().length() < 8){
                len1 = sco2.getName().length();
            }
            if(sco2.getDescription().length() < 8){
                len2 = sco2.getDescription().length();
            }

            name = String.format("%9s", sco2.getName().substring(0,len1));
            description = String.format("%9s", sco2.getDescription().substring(0,len2));
            all = String.format("%s %s %s %s %s %n", name, description, sco2.getLatitude(), sco2.getLongitude(), sco2.getLocation());


            result = result + Integer.valueOf(count).toString()
                    + "	|	"
                    + all;
//                    + " "
//                    + description
//                    + " "
//                    + sco2.getLatitude()
//                    + " "
//                    + sco2.getLongitude()
//                    + "\n";

            count++;

        }

        return result;
    }

    public void deleteEntry(String delet) throws SQLException{


        Log.d("DataBase", "entry deleted");
        Log.d("DataBase","entry deleted");
        Log.d("DataBase","entry deleted");

        ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + delet, null);
    }

    public void populate(){

        Log.d("DataBase", "populate");
        Log.d("DataBase", "populate");
        Log.d("DataBase", "populate");

        if(UpdateFlag.getUpdate() == 1) {


            //TODO: change policy
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            setThreadPolicy(policy);


            String input = readCentral();
            try {

                int count = 0;
                JSONObject json;
                //JsonReader jsonReader = Json.createReader(input);
                JSONArray array = new JSONArray(input);

                Log.i(SQLdb.class.getName(), array.toString());

                while (count < array.length()) {

                    json = array.getJSONObject(count);
                    this.createEntry(json.getString("name"), json.getString("description"), json.getString("latitude"), json.getString("longitude"), json.getString("location"));
                    count++;
                    System.out.println(json.toString());
                }


            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            UpdateFlag.setUpdate(0);
        }


    }

    public String readCentral() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://10.0.2.2:8000/getEvents");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(JsonParseActivity.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("DataBase", "readCentral");
        Log.d("DataBase", "readCentral");
        Log.d("DataBase", "readCentral");

        return builder.toString();
    }

}
