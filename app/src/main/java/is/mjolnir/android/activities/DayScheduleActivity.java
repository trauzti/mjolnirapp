package is.mjolnir.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import is.mjolnir.android.BuildConfig;
import is.mjolnir.android.R;
import is.mjolnir.android.api.MjolnirTimetableApiService;
import is.mjolnir.android.lists.Header;
import is.mjolnir.android.lists.Item;
import is.mjolnir.android.lists.ListItem;
import is.mjolnir.android.lists.TwoTextArrayAdapter;
import is.mjolnir.android.models.ClassAndTime;
import is.mjolnir.android.models.Timetable;
import is.mjolnir.android.models.timetable.Time;
import is.mjolnir.android.models.timetable.TimetableResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class DayScheduleActivity extends ActionBarActivity {

    public final String TAG = DayScheduleActivity.class.getName();
    private List<Item> items = new ArrayList<Item>();
    private TwoTextArrayAdapter adapter;
    private int day = 0;
    private String dayName = "";
    private MjolnirTimetableApiService timetableApiService;
    private RestAdapter restAdapter;



    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_schedule);
        Timetable.ensureRejectedMapNotNull(this);

        RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.NONE;

        Gson gson = new GsonBuilder()
                .create();

        if (BuildConfig.DEBUG) {
            logLevel = RestAdapter.LogLevel.BASIC;
            //logLevel = RestAdapter.LogLevel.FULL;
        }

        restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://s3-eu-west-1.amazonaws.com")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(logLevel)
                .build();

        lv = (ListView) findViewById(R.id.list);
        Intent i = getIntent();
        day = i.getIntExtra("day", 0);
        dayName = i.getStringExtra("dayName");

        //Log.d(TAG, "day=" + day);
        //Toast.makeText(this, "day=" + day, Toast.LENGTH_LONG).show();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(dayName);



        adapter = new TwoTextArrayAdapter(this, items);
        lv.setAdapter(adapter);
        setItems();

        timetableApiService = restAdapter.create(MjolnirTimetableApiService.class);

        timetableApiService.getTimeTable(new Callback<TimetableResponse>() {
            @Override
            public void success(TimetableResponse timetableResponse, Response response) {
                Time classtime = timetableResponse.day_1.get(0);
                System.out.println(classtime.room);
                System.out.println(classtime.teacher);
                System.out.println(classtime.time);
                System.out.println(classtime.title);
                setItems(timetableResponse);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }

    public void setItems(TimetableResponse timetableResponse) {
        items.clear();

        ArrayList<Time> times = new ArrayList<>();
        switch(day) {
            case 0:
                times = timetableResponse.day_1;
                break;
            case 1:
                times = timetableResponse.day_2;
                break;
            case 2:
                times = timetableResponse.day_3;
                break;
            case 3:
                times = timetableResponse.day_4;
                break;
            case 4:
                times = timetableResponse.day_5;
                break;
            case 5:
                times = timetableResponse.day_6;
                break;
            case 6:
                times = timetableResponse.day_7;
                break;
            default:
                break;
        }
        // TODO: Fis the rooms
        for (Time time : times) {
            items.add(new Header(time.room));
            //if (!Timetable.rejectedMap.get(time.title)) {
                items.add(new ListItem(time.time, time.title));
            //}

        }

        adapter.notifyDataSetChanged();
    }


    public void setItems() {
        items.clear();
        items.add(new Header("SALUR 1"));
        for (ClassAndTime classAndTime : Timetable.getClassesForWeekday(day, 1)) {
            if (!Timetable.rejectedMap.get(classAndTime.name)) {
                items.add(new ListItem(classAndTime.time, classAndTime.name));
            }
        }
        items.add(new Header("SALUR 2"));
        for (ClassAndTime classAndTime : Timetable.getClassesForWeekday(day, 2)) {
            if (!Timetable.rejectedMap.get(classAndTime.name)) {
                items.add(new ListItem(classAndTime.time, classAndTime.name));
            }
        }

        items.add(new Header("SALUR 3"));
        for (ClassAndTime classAndTime : Timetable.getClassesForWeekday(day, 3)) {
            if (!Timetable.rejectedMap.get(classAndTime.name)) {
                items.add(new ListItem(classAndTime.time, classAndTime.name));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_customize) {
            // TODO: Slide up
            startActivity(new Intent(DayScheduleActivity.this, CustomizeClasses.class));
            return true;
        }

        if (id == android.R.id.home) {
            //  The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        //        finish();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Timetable.needToRefreshRejectedClasses) {
            Timetable.needToRefreshRejectedClasses = false;
            //Log.d(TAG, "Refreshing rejected classes");
            setItems();
        }
    }
}
